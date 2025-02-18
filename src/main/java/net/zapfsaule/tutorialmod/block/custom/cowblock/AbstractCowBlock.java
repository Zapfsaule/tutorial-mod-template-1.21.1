package net.zapfsaule.tutorialmod.block.custom.cowblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCowBlock extends HorizontalFacingBlock {
    protected static VoxelShape SHAPEN;
    protected static VoxelShape SHAPES;
    protected static VoxelShape SHAPEW;
    protected static VoxelShape SHAPEE;

    public AbstractCowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

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

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        return rotateShape(facing);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    // Diese Methoden werden in jeder Unterklasse individuell implementiert
    protected abstract Block getReplacementBlock();
    protected abstract Item getRequiredItem();
    protected abstract Item getDroppedItem();
    protected abstract int getMinDropAmount();
    protected abstract int getMaxDropAmount();


    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && stack.getItem() == getRequiredItem()) {
            Direction facing = state.get(FACING);

            // Ersetze den Block durch die nächste Stufe
            world.setBlockState(pos, getReplacementBlock().getDefaultState().with(FACING, facing), Block.NOTIFY_ALL);

            // Droppe die Items
            int randomAmount = getMinDropAmount() + world.getRandom().nextInt(getMaxDropAmount() - getMinDropAmount() + 1);
            ItemEntity droppedItem = new ItemEntity((ServerWorld) world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    new ItemStack(getDroppedItem(), randomAmount));
            world.spawnEntity(droppedItem);

            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    // Unterklassen müssen den CODEC bereitstellen
    protected abstract MapCodec<? extends HorizontalFacingBlock> getCodec();




}
