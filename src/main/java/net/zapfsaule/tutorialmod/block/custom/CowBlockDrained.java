package net.zapfsaule.tutorialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CowBlockDrained extends HorizontalFacingBlock {
    public static final MapCodec<CowBlockDrained> CODEC = createCodec(CowBlockDrained::new);
    private static final VoxelShape SHAPEN = Block.createCuboidShape(2, 0, 4, 14, 16, 14);
    private static final VoxelShape SHAPEE = Block.createCuboidShape(2, 0, 2, 12, 16, 14);
    private static final VoxelShape SHAPES = Block.createCuboidShape(2, 0, 2, 14, 16, 12);
    private static final VoxelShape SHAPEW = Block.createCuboidShape(4, 0, 2, 14, 16, 14);


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

    public CowBlockDrained(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState());
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
        if (!world.isClient() && stack.getItem() == Items.EMERALD) {

            world.setBlockState(pos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL);

            // Diamanten droppen
            int randomAmount = 3 + world.getRandom().nextInt(4);
            ItemEntity droppedItem = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    new ItemStack(Items.GOLD_INGOT, randomAmount));
            world.spawnEntity(droppedItem);


            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
