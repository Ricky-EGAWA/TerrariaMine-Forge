package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.client.GoldenElytraLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    // コンストラクタのダミー実装（Mixinでは必要）
    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    // PlayerRenderer のコンストラクタに割り込み、GoldenElytraLayer を追加
    @Inject(method = "<init>", at = @At("TAIL"))
    private void addGoldenElytraLayer(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
        System.out.println("Adding GoldenElytraLayer to PlayerRenderer...");
        this.addLayer(new GoldenElytraLayer<>(this, context.getModelSet()));
    }
}
