package com.ricky.xp_giant_mod;

import net.minecraft.world.entity.player.Player;

public class ScaleManager {
    public static float getScaleForPlayer(Player player) {
        int experienceLevel = player.experienceLevel;

        if (experienceLevel == 0) {
            return 0.25f;
        } else if (experienceLevel == 1) {
            return 0.5f;
        } else if (experienceLevel <= 5) {
            return 1.0f;
        } else if (experienceLevel <= 10) {
            return 2.5f;
        } else if (experienceLevel <= 20) {
            return 5.0f;
        } else if (experienceLevel <= 30) {
            return 6.0f;
        } else if (experienceLevel <= 40) {
            return 10.0f;
        } else if (experienceLevel <= 50) {
            return 12.5f;
        } else if (experienceLevel <= 60) {
            return 15.0f;
        } else if (experienceLevel <= 70) {
            return 20.0f;
        } else if (experienceLevel <= 80) {
            return 25.0f;
        } else if (experienceLevel <= 90) {
            return 30.0f;
        } else {
            return 32.5f; // レベル100を超える場合の最大スケール
        }
    }
}
