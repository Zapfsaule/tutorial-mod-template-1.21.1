package net.zapfsaule.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.zapfsaule.tutorialmod.block.ChainTransformHandler;
import net.zapfsaule.tutorialmod.block.ModBlockEntities;
import net.zapfsaule.tutorialmod.block.ModBlocks;
import net.zapfsaule.tutorialmod.component.ModDataComponentTypes;
import net.zapfsaule.tutorialmod.item.ModItemGroups;
import net.zapfsaule.tutorialmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;


public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {;

		ModBlockEntities.registerBlockEntities();

		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModDataComponentTypes.registerDataComponentTypes();

		FuelRegistry.INSTANCE.add(ModItems.STARLIGHT_ASHES, 600);

		UseBlockCallback.EVENT.register(ChainTransformHandler::handleChainTransformation);

	}
}