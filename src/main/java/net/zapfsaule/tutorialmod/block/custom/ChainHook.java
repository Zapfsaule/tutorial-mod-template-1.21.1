package net.zapfsaule.tutorialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.zapfsaule.tutorialmod.block.custom.cowblock.CowBlock;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ChainHook extends HorizontalFacingBlock {
    public static final MapCodec<CowBlock> CODEC = createCodec(CowBlock::new);
    private static final VoxelShape SHAPEN = Stream.of(
                    Block.createCuboidShape(0, 6.5, 6.5, 16, 9.5, 9.5),
                    Block.createCuboidShape(7.5, 4, 4, 8.5, 10, 10)
            ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR))
            .orElse(VoxelShapes.empty());
    private static final VoxelShape SHAPEE = Stream.of(
                    Block.createCuboidShape(6.5, 6.5, 0, 9.5, 9.5, 16),
                    Block.createCuboidShape(6, 4, 7.5, 12, 10, 8.5)
            ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR))
            .orElse(VoxelShapes.empty());
    private static final VoxelShape SHAPES = Stream.of(
                    Block.createCuboidShape(0, 6.5, 6.5, 16, 9.5, 9.5),
                    Block.createCuboidShape(7.5, 4, 6, 8.5, 10, 12)
            ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR))
            .orElse(VoxelShapes.empty());
    private static final VoxelShape SHAPEW = Stream.of(
                    Block.createCuboidShape(6.5, 6.5, 0, 9.5, 9.5, 16),
                    Block.createCuboidShape(4, 4, 7.5, 10, 10, 8.5)
            ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR))
            .orElse(VoxelShapes.empty());


    private static VoxelShape rotateShape(Direction direction) {
        switch (direction) {
            case NORTH:
                return SHAPEN;
            case SOUTH:
                return SHAPES;
            case WEST:
                return SHAPEW;
            case EAST:
                return SHAPEE;
            default:
                return SHAPEN;
        }
    }


    public ChainHook(Settings settings) {
        super(settings);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        return rotateShape(facing);
    }


    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            // Check if the item used is an emerald (or any other item)
            if (stack.getItem() == Items.EMERALD) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState()); // Change block to dirt

                int randomAmount = 3 + world.getRandom().nextInt(4); //Random Amount between 3 and 6
                // Drop an item (e.g., a diamond)
                ItemEntity droppedItem = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(Items.DIAMOND, randomAmount));
                world.spawnEntity(droppedItem);
                return ItemActionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
