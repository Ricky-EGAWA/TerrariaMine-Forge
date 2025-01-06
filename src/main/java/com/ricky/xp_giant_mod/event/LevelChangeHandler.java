package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = XPGiantMod.MOD_ID)
public class LevelChangeHandler {
    private static final Map<ServerPlayer, Integer> playerLevels = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        int currentLevel = serverPlayer.experienceLevel;
        int previousLevel = playerLevels.getOrDefault(serverPlayer, -1);

        if (currentLevel != previousLevel) {
            playerLevels.put(serverPlayer, currentLevel);
            System.out.println("Player XP Level changed: " + currentLevel);

            if (currentLevel == 1) {
                System.out.println("level 1");
                serverPlayer.sendSystemMessage(Component.literal("[レベル１] アンロック チャージジャンプ"));
            } else if (currentLevel == 5) {
                System.out.println("level 5");
                serverPlayer.sendSystemMessage(Component.literal("[レベル5]"));
            } else if (currentLevel == 10) {
                System.out.println("level 10");
                serverPlayer.sendSystemMessage(Component.literal("[レベル10]"));
            } else if (currentLevel == 20) {
                System.out.println("level 20");
                serverPlayer.sendSystemMessage(Component.literal("[レベル30] アンロック 3×3 の範囲採掘"));
                serverPlayer.sendSystemMessage(Component.literal("[レベル20] アンロック モブを踏み潰す"));
            } else if (currentLevel == 30) {
                System.out.println("level 30");
                serverPlayer.sendSystemMessage(Component.literal("[レベル30] アンロック 5×5 の範囲採掘"));
            } else if (currentLevel == 40) {
                System.out.println("level 40");
                serverPlayer.sendSystemMessage(Component.literal("[レベル40] アンロック 体力増加"));
            } else if (currentLevel == 50) {
                System.out.println("level 50");
                serverPlayer.sendSystemMessage(Component.literal("[レベル50] アンロック 9×9 の範囲採掘"));
                serverPlayer.sendSystemMessage(Component.literal("[レベル50] アンロック 攻撃力増加"));
            } else if (currentLevel == 60) {
                System.out.println("level 60");
                serverPlayer.sendSystemMessage(Component.literal("[レベル60] アンロック チャージジャンプで破壊81×50×81"));
                serverPlayer.sendSystemMessage(Component.literal("[レベル60] アンロック 素手で破壊50×50×50"));
            } else if (currentLevel == 70) {
                System.out.println("level 70");
                serverPlayer.sendSystemMessage(Component.literal("[レベル70] アンロック 13×13 の範囲採掘"));
            } else if (currentLevel == 80) {
                System.out.println("level 80");
                serverPlayer.sendSystemMessage(Component.literal("[レベル80] アンロック 体力増加"));
                serverPlayer.sendSystemMessage(Component.literal("[レベル80] アンロック 攻撃力増加"));


                dropItem(serverPlayer.level(), serverPlayer.getOnPos(), new ItemStack(ModItems.GIANT_ZOMBIE_SPAWN_EGG.get()));
            } else if (currentLevel == 90) {
                System.out.println("level 90");
                serverPlayer.sendSystemMessage(Component.literal("[レベル90] アンロック 攻撃力増加"));
            } else if (currentLevel == 100) {
                System.out.println("level 100");
                serverPlayer.sendSystemMessage(Component.literal("[レベル100]"));
            }
        }
    }
    private static void dropItem(Level level, BlockPos pos, ItemStack itemStack) {
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack));
    }
}

