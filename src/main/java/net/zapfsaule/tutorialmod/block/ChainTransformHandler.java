package net.zapfsaule.tutorialmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.zapfsaule.tutorialmod.block.custom.ChainHook;
import net.zapfsaule.tutorialmod.item.ModItems;

public class ChainTransformHandler {
    public static ActionResult handleChainTransformation(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!world.isClient) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState blockState = world.getBlockState(pos);

            if (blockState.getBlock() == Blocks.CHAIN) {
                ItemStack itemStack = player.getStackInHand(hand);
                Item requiredItem = ModItems.HOOK;

                if (itemStack.getItem() == requiredItem) {
                    // Prüfen, ob die Chain horizontal ist
                    if (blockState.contains(Properties.AXIS)) {
                        Direction.Axis axis = blockState.get(Properties.AXIS);
                        if (axis == Direction.Axis.X || axis == Direction.Axis.Z) { // Nur horizontale Chains umwandeln

                            Direction hookFacing;

                            // Spielerposition relativ zur Chain bestimmen
                            double playerX = player.getX();
                            double playerZ = player.getZ();
                            double blockX = pos.getX() + 0.5; // Block-Mitte
                            double blockZ = pos.getZ() + 0.5; // Block-Mitte

                            if (axis == Direction.Axis.X) {
                                // Chain verläuft horizontal entlang der X-Achse → Hook zeigt nach Norden oder Süden
                                hookFacing = (playerZ < blockZ) ? Direction.NORTH : Direction.SOUTH;
                            } else {
                                // Chain verläuft vertikal entlang der Z-Achse → Hook zeigt nach Westen oder Osten
                                hookFacing = (playerX < blockX) ? Direction.WEST : Direction.EAST;
                            }

                            // ChainHook-Block mit richtiger Ausrichtung setzen
                            BlockState chainHookState = ModBlocks.CHAIN_HOOK.getDefaultState()
                                    .with(ChainHook.FACING, hookFacing);

                            world.setBlockState(pos, chainHookState);

                            // Item aus Inventar entfernen
                            if (player instanceof ServerPlayerEntity) {
                                itemStack.decrement(1);
                            }

                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
}
