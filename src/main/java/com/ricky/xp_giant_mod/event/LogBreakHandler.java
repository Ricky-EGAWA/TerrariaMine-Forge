package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.UUID;

@Mod.EventBusSubscriber
public class LogBreakHandler {

    // プレイヤーごとの原木破壊済み状態を管理するセット
    private static final HashSet<UUID> playersWhoBrokeLog = new HashSet<>();
    private static final HashSet<UUID> playersWhoBrokeCoal = new HashSet<>();
    private static final HashSet<UUID> playersWhoBrokeIron = new HashSet<>();
    private static final HashSet<UUID> playersWhoBrokeDiamond = new HashSet<>();
    private static final HashSet<UUID> playersWhoBrokeEmerald = new HashSet<>();
    private static final HashSet<UUID> playersWhoBrokePressure = new HashSet<>();

    @SubscribeEvent
    public static void onLogBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == null) return;

        Player player = event.getPlayer();

        // 破壊されたブロックが原木であるかをチェック
        if (!playersWhoBrokeLog.contains(player.getUUID()) && (event.getState().getBlock() == Blocks.OAK_LOG ||
                event.getState().getBlock() == Blocks.SPRUCE_LOG ||
                event.getState().getBlock() == Blocks.BIRCH_LOG ||
                event.getState().getBlock() == Blocks.JUNGLE_LOG ||
                event.getState().getBlock() == Blocks.ACACIA_LOG ||
                event.getState().getBlock() == Blocks.DARK_OAK_LOG)) {

            // プレイヤーをセットに追加（初回破壊済みと記録）
            playersWhoBrokeLog.add(player.getUUID());

            // XPItem をドロップ
            BlockPos pos = event.getPos();
            ItemStack xpItemStack = new ItemStack(ModItems.SMALL_XP.get());
            player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), xpItemStack));
        }
        if (!playersWhoBrokeCoal.contains(player.getUUID()) && (event.getState().getBlock() == Blocks.COAL_ORE ||
                event.getState().getBlock() == Blocks.DEEPSLATE_COAL_ORE)) {

            // プレイヤーをセットに追加（初回破壊済みと記録）
            playersWhoBrokeCoal.add(player.getUUID());

            // XPItem をドロップ
            BlockPos pos = event.getPos();
            ItemStack xpItemStack = new ItemStack(ModItems.MEDIUM_XP.get());
            player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), xpItemStack));
        }
        if (!playersWhoBrokeIron.contains(player.getUUID()) && (event.getState().getBlock() == Blocks.IRON_ORE ||
                event.getState().getBlock() == Blocks.DEEPSLATE_IRON_ORE) && playersWhoBrokeCoal.contains(player.getUUID())) {

            // プレイヤーをセットに追加（初回破壊済みと記録）
            playersWhoBrokeIron.add(player.getUUID());

            // XPItem をドロップ
            BlockPos pos = event.getPos();
            ItemStack xpItemStack = new ItemStack(ModItems.MEDIUM_XP.get());
            player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), xpItemStack));
        }
        if (!playersWhoBrokeDiamond.contains(player.getUUID()) && (event.getState().getBlock() == Blocks.DIAMOND_ORE ||
                event.getState().getBlock() == Blocks.DEEPSLATE_DIAMOND_ORE) && playersWhoBrokeIron.contains(player.getUUID())) {

            // プレイヤーをセットに追加（初回破壊済みと記録）
            playersWhoBrokeDiamond.add(player.getUUID());

            // XPItem をドロップ
            BlockPos pos = event.getPos();
            ItemStack xpItemStack = new ItemStack(ModItems.XP.get());
            player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), xpItemStack));
        }
        if (!playersWhoBrokeEmerald.contains(player.getUUID()) && (event.getState().getBlock() == Blocks.EMERALD_BLOCK) && playersWhoBrokeDiamond.contains(player.getUUID())) {

            // プレイヤーをセットに追加（初回破壊済みと記録）
            playersWhoBrokeEmerald.add(player.getUUID());

            // XPItem をドロップ
            BlockPos pos = event.getPos();
            ItemStack xpItemStack = new ItemStack(ModItems.XP.get());
            player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), xpItemStack));
        }
        if (!playersWhoBrokePressure.contains(player.getUUID()) && (event.getState().getBlock() == Blocks.LIGHT_GRAY_CONCRETE) && playersWhoBrokeEmerald.contains(player.getUUID())) {

            // プレイヤーをセットに追加（初回破壊済みと記録）
            playersWhoBrokePressure.add(player.getUUID());

            // XPItem をドロップ
            BlockPos pos = event.getPos();
            ItemStack xpItemStack = new ItemStack(ModItems.XP.get());
            player.level().addFreshEntity(new ItemEntity(player.level(), pos.getX(), pos.getY(), pos.getZ(), xpItemStack));
        }
    }
}
