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
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.zapfsaule.tutorialmod.block.ModBlocks;
import net.zapfsaule.tutorialmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class CowBlockKilled3 extends AbstractCowBlock {

    public static final MapCodec<CowBlockKilled3> CODEC = createCodec(CowBlockKilled3::new);

    static {
        SHAPEN = Block.createCuboidShape(2, 0, 4, 14, 16, 14);
        SHAPEE = Block.createCuboidShape(2, 0, 2, 12, 16, 14);
        SHAPES = Block.createCuboidShape(2, 0, 2, 14, 16, 12);
        SHAPEW = Block.createCuboidShape(4, 0, 2, 14, 16, 14);
    }

    public CowBlockKilled3(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected Block getReplacementBlock() {
        return ModBlocks.COW_BLOCK_KILLED_4;
    }

    @Override
    protected Item getRequiredItem() {
        return ModItems.BUTCHER_KNIFE;
    }

    @Override
    protected Item getDroppedItem() {
        return Items.GOLD_INGOT;
    }

    @Override
    protected int getMinDropAmount() {
        return 5;
    }

    @Override
    protected int getMaxDropAmount() {
        return 6;
    }

}
