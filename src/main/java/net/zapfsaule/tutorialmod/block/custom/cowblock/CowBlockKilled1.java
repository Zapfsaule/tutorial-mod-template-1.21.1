package net.zapfsaule.tutorialmod.block.custom.cowblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.zapfsaule.tutorialmod.block.ModBlocks;
import net.zapfsaule.tutorialmod.item.ModItems;

public class CowBlockKilled1 extends AbstractCowBlock {

    public static final MapCodec<CowBlockKilled1> CODEC = createCodec(CowBlockKilled1::new);

    static {
        SHAPEN = Block.createCuboidShape(2, 0, 4, 14, 16, 14);
        SHAPEE = Block.createCuboidShape(2, 0, 2, 12, 16, 14);
        SHAPES = Block.createCuboidShape(2, 0, 2, 14, 16, 12);
        SHAPEW = Block.createCuboidShape(4, 0, 2, 14, 16, 14);
    }

    public CowBlockKilled1(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected Block getReplacementBlock() {
        return ModBlocks.COW_BLOCK_KILLED_2;
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
        return 3;
    }

    @Override
    protected int getMaxDropAmount() {
        return 6;
    }
}
