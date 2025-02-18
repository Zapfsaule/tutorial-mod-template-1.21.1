package net.zapfsaule.tutorialmod.block.custom.cowblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CowCorpse extends HorizontalFacingBlock {
    public static final MapCodec<CowBlock> CODEC = createCodec(CowBlock::new);
    private static final VoxelShape SHAPEN = Block.createCuboidShape(0, 0, 4, 16, 12, 14);
    private static final VoxelShape SHAPEE = Block.createCuboidShape(2, 0, 0, 12, 12, 16);
    private static final VoxelShape SHAPES = Block.createCuboidShape(0, 0, 2, 16, 12, 12);
    private static final VoxelShape SHAPEW = Block.createCuboidShape(4, 0, 0, 14, 12, 16);


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


    public CowCorpse(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        return rotateShape(facing);
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
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }
}
