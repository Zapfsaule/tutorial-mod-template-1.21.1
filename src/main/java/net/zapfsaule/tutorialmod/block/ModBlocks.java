package net.zapfsaule.tutorialmod.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.zapfsaule.tutorialmod.TutorialMod;
import net.zapfsaule.tutorialmod.block.custom.*;
import net.zapfsaule.tutorialmod.block.custom.cowblock.*;

public class ModBlocks {

    public static final Block PINK_GARNET_BLOCK = registerBlock("pink_garnet_block",
            new Block(AbstractBlock.Settings.create().strength(4f)
                    .requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block RAW_PINK_GARNET_BLOCK = registerBlock("raw_pink_garnet_block",
            new Block(AbstractBlock.Settings.create().strength(3f)
                    .requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block PINK_GARNET_ORE = registerBlock("pink_garnet_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2,5),
                    AbstractBlock.Settings.create().strength(3f).requiresTool()));
    public static final Block PINK_GARNET_DEEPSLATE_ORE = registerBlock("pink_garnet_deepslate_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2,5),
                    AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
            new MagicBlock(AbstractBlock.Settings.create().strength(1f).requiresTool()));

    public static final Block PINK_GARNET_STAIRS = registerBlock("pink_garnet_stairs",
            new StairsBlock(ModBlocks.PINK_GARNET_BLOCK.getDefaultState(),
                    AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block PINK_GARNET_SLAB = registerBlock("pink_garnet_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));

    public static final Block PINK_GARNET_BUTTON = registerBlock("pink_garnet_button",
            new ButtonBlock(BlockSetType.IRON, 2,AbstractBlock.Settings.create().strength(2f).requiresTool().noCollision()));
    public static final Block PINK_GARNET_PRESSURE_PLATE = registerBlock("pink_garnet_pressure_plate",
            new PressurePlateBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(2f).requiresTool()));

    public static final Block PINK_GARNET_FENCE = registerBlock("pink_garnet_fence",
            new FenceBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block PINK_GARNET_FENCE_GATE = registerBlock("pink_garnet_fence_gate",
            new FenceGateBlock(WoodType.ACACIA,AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block PINK_GARNET_WALL = registerBlock("pink_garnet_wall",
            new WallBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));

    public static final Block PINK_GARNET_DOOR = registerBlock("pink_garnet_door",
            new DoorBlock(BlockSetType.IRON,AbstractBlock.Settings.create().strength(2f).requiresTool().nonOpaque()));
    public static final Block PINK_GARNET_TRAPDOOR = registerBlock("pink_garnet_trapdoor",
            new TrapdoorBlock(BlockSetType.IRON,AbstractBlock.Settings.create().strength(2f).requiresTool().nonOpaque()));


    public static final Block PINK_GARNET_LAMP = registerBlock("pink_garnet_lamp",
            new PinkGarnetLampBlock(AbstractBlock.Settings.create()
                    .strength(1f).requiresTool().luminance(state -> state.get(PinkGarnetLampBlock.CLICKED) ? 15 : 0)));

    public static final Block COW_BLOCK = registerBlock("cow_block",
            new CowBlock(AbstractBlock.Settings.create().strength(2f).nonOpaque()));
    public static final Block COW_BLOCK_DRAINED = registerBlock("cow_block_drained",
            new CowBlockDrained(AbstractBlock.Settings.create().strength(2f).nonOpaque()));
    public static final Block COW_BLOCK_KILLED_1 = registerBlock("cow_block_killed_1",
            new CowBlockKilled1(AbstractBlock.Settings.create().strength(2f).nonOpaque()));
    public static final Block COW_BLOCK_KILLED_2 = registerBlock("cow_block_killed_2",
            new CowBlockKilled2(AbstractBlock.Settings.create().strength(2f).nonOpaque()));
    public static final Block COW_BLOCK_KILLED_3 = registerBlock("cow_block_killed_3",
            new CowBlockKilled3(AbstractBlock.Settings.create().strength(2f).nonOpaque()));
    public static final Block COW_BLOCK_KILLED_4 = registerBlock("cow_block_killed_4",
            new CowBlockKilled4(AbstractBlock.Settings.create().strength(2f).nonOpaque()));
    public static final Block COW_CORPSE = registerBlock("cow_corpse",
            new CowCorpse(AbstractBlock.Settings.create().strength(2f).nonOpaque()));


    public static final Block CHAIN_HOOK = registerBlock("chain_hook",
            new ChainHook(AbstractBlock.Settings.create().strength(2f).nonOpaque()));



    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(TutorialMod.MOD_ID, name), block);
    }


    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, name),
            new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        TutorialMod.LOGGER.info("Registering Mod Blocks for " + TutorialMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries ->{
            entries.add(ModBlocks.PINK_GARNET_BLOCK);
            entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);
        });
    }



}
