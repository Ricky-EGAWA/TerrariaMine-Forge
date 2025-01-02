package com.ricky.xp_giant_mod;

import net.minecraft.world.entity.player.Player;

public class ScaleManager {
    public static float getScaleForPlayer(Player player) {
        int experienceLevel = player.experienceLevel;

        if (experienceLevel <= 5) {
            return 1.0f;
        } else if (experienceLevel <= 10) {
            return 2.5f;
        } else if (experienceLevel <= 20) {
            return 5.0f;
        } else {
            return 7.5f; // レベル20を超える場合のスケール
        }
    }
}
