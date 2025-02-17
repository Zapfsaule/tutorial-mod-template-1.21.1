package net.zapfsaule.tutorialmod.block;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.zapfsaule.tutorialmod.TutorialMod;
import net.zapfsaule.tutorialmod.block.custom.meatgrinder.MeatGrinderBlockEntity;


public class ModBlockEntities {


    public static final BlockEntityType<MeatGrinderBlockEntity> MEAT_GRINDER_BLOCK_ENTITY = register(
            "meat_grinder",
            BlockEntityType.Builder.create(MeatGrinderBlockEntity::new, ModBlocks.MEAT_GRINDER).build()
    );

    private static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(TutorialMod.MOD_ID, path), blockEntityType);
    }

    public static void registerBlockEntities() {
        TutorialMod.LOGGER.info("Registering ModBlockEntities...");
    }
}
