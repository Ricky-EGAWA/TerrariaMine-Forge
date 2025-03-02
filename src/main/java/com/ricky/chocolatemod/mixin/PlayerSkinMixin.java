package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerSkinMixin {
    @Inject(method = "getTextureLocation", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(AbstractClientPlayer pEntity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (pEntity.getPersistentData().getBoolean("gold_man")) {
            // カスタムスキンに変更
            cir.setReturnValue(new ResourceLocation(ChocolateMod.MOD_ID, "textures/entity/gold_skin.png"));
        }
    }
}
