package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber
public class BlockBreakHandler {

    public static final HashSet<UUID> playersWhoBrokeLog = new HashSet<>();
    public static final HashSet<UUID> playersWhoBrokeCoal = new HashSet<>();
    public static final HashSet<UUID> playersWhoBrokeIron = new HashSet<>();
    public static final HashSet<UUID> playersWhoBrokeDiamond = new HashSet<>();
    public static final HashSet<UUID> playersWhoBrokeEmerald = new HashSet<>();
    public static final HashSet<UUID> playersWhoBrokePressure = new HashSet<>();

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        ItemStack itemStack = player.getMainHandItem();
        Level level = player.level();
        BlockPos pos = event.getPos();

        // Handle XP and progression items from specific blocks
        handleProgressionItems(event, player, pos);

        // Check for ExplodePickaxe or high-level hand break
        if (itemStack.getItem() instanceof PickaxeItem) {
            breakBlocks(level, pos, player);
        } else if (itemStack.isEmpty() && player.experienceLevel >= 60) {
            breakBlocksHand(level, pos, player);
        }
    }

    private static void handleProgressionItems(BlockEvent.BreakEvent event, Player player, BlockPos pos) {
        Level level = player.level();
        Block block = event.getState().getBlock();

        if (!playersWhoBrokeLog.contains(player.getUUID()) &&
                (block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG ||
                        block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG || block == Blocks.DARK_OAK_LOG)) {

            playersWhoBrokeLog.add(player.getUUID());
            dropItem(level, pos, new ItemStack(ModItems.SMALL_XP.get()));
        }
        if (!playersWhoBrokeCoal.contains(player.getUUID()) &&
                (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE)) {

            playersWhoBrokeCoal.add(player.getUUID());
            dropItem(level, pos, new ItemStack(ModItems.MEDIUM_XP.get()));
        }
        if (!playersWhoBrokeIron.contains(player.getUUID()) &&
                (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) && playersWhoBrokeCoal.contains(player.getUUID())) {

            playersWhoBrokeIron.add(player.getUUID());
            dropItem(level, pos, new ItemStack(ModItems.MEDIUM_XP.get()));
        }
        if (!playersWhoBrokeDiamond.contains(player.getUUID()) &&
                (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) && playersWhoBrokeIron.contains(player.getUUID())) {

            playersWhoBrokeDiamond.add(player.getUUID());
            dropItem(level, pos, new ItemStack(ModItems.XP.get()));
        }
        if (!playersWhoBrokeEmerald.contains(player.getUUID()) &&
                block == Blocks.EMERALD_BLOCK && playersWhoBrokeDiamond.contains(player.getUUID())) {

            playersWhoBrokeEmerald.add(player.getUUID());
            dropItem(level, pos, new ItemStack(ModItems.XP.get()));
        }
        if (!playersWhoBrokePressure.contains(player.getUUID()) &&
                block == Blocks.LIGHT_GRAY_CONCRETE && playersWhoBrokeEmerald.contains(player.getUUID())) {

            playersWhoBrokePressure.add(player.getUUID());
            dropItem(level, pos, new ItemStack(ModItems.XP.get()));
            dropItem(level, pos, new ItemStack(ModItems.GIANT_ZOMBIE_SPAWN_EGG.get()));
        }
    }

    private static void dropItem(Level level, BlockPos pos, ItemStack itemStack) {
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack));
    }

    public static void breakBlocks(Level level, BlockPos pos, Player player) {
        int experienceLevel = player.experienceLevel;
        int r;
        if (experienceLevel < 20) {
            r = 0;
        } else if (experienceLevel < 30) {
            r = 1;
        } else if (experienceLevel < 40) {
            r = 2;
        } else if (experienceLevel < 60) {
            r = 4;
        } else {
            r = 6;
        }
        destroySurroundingBlocks(level, pos, player, r);
    }

    public static void breakBlocksHand(Level level, BlockPos pos, Player player) {
        destroySurroundingBlocks(level, pos, player, 25);
    }

    private static void destroySurroundingBlocks(Level level, BlockPos pos, Player player, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos offsetPos = pos.offset(x, y, z);
                    if (!offsetPos.equals(pos)) {
                        BlockState state = level.getBlockState(offsetPos);
                        Block block = state.getBlock();
                        if (!state.isAir() && block != Blocks.BEDROCK) {
                            boolean dropItems = isOreBlock(block);
                            level.destroyBlock(offsetPos, dropItems, player);
                        }
                    }
                }
            }
        }
    }

    private static boolean isOreBlock(Block block) {
        return block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE || block == Blocks.DIAMOND_ORE ||
                block == Blocks.COAL_ORE || block == Blocks.LAPIS_ORE || block == Blocks.EMERALD_ORE ||
                block == Blocks.REDSTONE_ORE || block == Blocks.NETHER_QUARTZ_ORE || block == Blocks.ANCIENT_DEBRIS ||
                block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_GOLD_ORE ||
                block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_COAL_ORE ||
                block == Blocks.DEEPSLATE_LAPIS_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE ||
                block == Blocks.DEEPSLATE_REDSTONE_ORE;
    }
}
