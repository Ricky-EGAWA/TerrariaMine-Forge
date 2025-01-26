package com.ricky.chocolatemod.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void preventHungerReduction(Player player, CallbackInfo ci) {
        // tickメソッドの実行をキャンセル
        if (!player.getPersistentData().getBoolean("crafted_chocolate_message_shown")) {
            ci.cancel();
        }
    }
}
