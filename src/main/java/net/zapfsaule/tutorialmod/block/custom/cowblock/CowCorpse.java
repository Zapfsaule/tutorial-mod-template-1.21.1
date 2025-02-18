package net.zapfsaule.tutorialmod.block.custom.cowblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CowCorpse extends AbstractCowBlock {

    public static final MapCodec<CowBlock> CODEC = createCodec(CowBlock::new);

    static {
        SHAPEN = Block.createCuboidShape(0, 0, 4, 16, 12, 14);
        SHAPEE = Block.createCuboidShape(2, 0, 0, 12, 12, 16);
        SHAPES = Block.createCuboidShape(0, 0, 2, 16, 12, 12);
        SHAPEW = Block.createCuboidShape(4, 0, 0, 14, 12, 16);
    }

    public CowCorpse(Settings settings) {
        super(settings);
    }

    @Override
    protected Block getReplacementBlock() {
        return null;
    }

    @Override
    protected Item getRequiredItem() {
        return null;
    }

    @Override
    protected Item getDroppedItem() {
        return null;
    }

    @Override
    protected int getMinDropAmount() {
        return 0;
    }

    @Override
    protected int getMaxDropAmount() {
        return 0;
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
