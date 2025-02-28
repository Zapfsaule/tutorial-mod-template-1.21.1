package net.zapfsaule.tutorialmod.block.custom.cowblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
import net.zapfsaule.tutorialmod.block.ModBlocks;
import net.zapfsaule.tutorialmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class CowBlock extends AbstractCowBlock {

    public static final MapCodec<CowBlock> CODEC = createCodec(CowBlock::new);
    public static final BooleanProperty TICKING = BooleanProperty.of("ticking");
    public static final IntProperty TICKS_LEFT = IntProperty.of("ticks_left", 0, 600); // Max 30 Sekunden (600 Ticks)

    static {
        SHAPEN = Block.createCuboidShape(2, 0, 4, 14, 16, 14);
        SHAPEE = Block.createCuboidShape(2, 0, 2, 12, 16, 14);
        SHAPES = Block.createCuboidShape(2, 0, 2, 14, 16, 12);
        SHAPEW = Block.createCuboidShape(4, 0, 2, 14, 16, 14);
    }

    public CowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(TICKING, false).with(TICKS_LEFT, 600));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TICKING, TICKS_LEFT);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(TICKING);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(TICKING)) {
            for (int i = 0; i < 3; i++) {
                world.addParticle(ParticleTypes.DRIPPING_HONEY,
                        pos.getX() + 0.5 + random.nextDouble() * 0.4 - 0.2,
                        pos.getY() - 0.1,
                        pos.getZ() + 0.5 + random.nextDouble() * 0.4 - 0.2,
                        0, -0.05, 0);
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (state.get(TICKING)) {
            int ticksRemaining = state.get(TICKS_LEFT);

            if (ticksRemaining > 0) {
                world.setBlockState(pos, state.with(TICKS_LEFT, ticksRemaining - 20), Block.NOTIFY_ALL);
                world.scheduleBlockTick(pos, this, 20);
            } else {

                Direction facing = state.get(FACING);

                // Direkt auf Dirt setzen
                world.setBlockState(pos, ModBlocks.COW_BLOCK_DRAINED.getDefaultState().with(FACING, facing), Block.NOTIFY_ALL);

                // Diamanten droppen
                int randomAmount = 3 + world.getRandom().nextInt(4);
                ItemEntity droppedItem = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                        new ItemStack(Items.DIAMOND, randomAmount));
                world.spawnEntity(droppedItem);
            }
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && stack.getItem() == ModItems.BUTCHER_KNIFE) {
            System.out.println("🎯 Rechtsklick erkannt! Timer gestartet bei: " + pos);

            world.setBlockState(pos, state.with(TICKING, true).with(TICKS_LEFT, 600));
            world.scheduleBlockTick(pos, this, 20);

            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
