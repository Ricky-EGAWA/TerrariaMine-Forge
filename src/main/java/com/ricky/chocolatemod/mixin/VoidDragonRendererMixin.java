package com.ricky.chocolatemod.mixin;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonRenderer.class)
public class VoidDragonRendererMixin {

    @Shadow
    @Final
    @Mutable
    private static ResourceLocation DRAGON_LOCATION;

    @Shadow
    @Final
    @Mutable
    private static RenderType RENDER_TYPE;

    @Shadow
    @Final
    @Mutable
    private static RenderType DECAL;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyDragonTexture(CallbackInfo ci) {
        DRAGON_LOCATION = new ResourceLocation(ChocolateMod.MOD_ID, "textures/entity/void_dragon.png");
        RENDER_TYPE = RenderType.entityCutoutNoCull(DRAGON_LOCATION);
        DECAL = RenderType.entityDecal(DRAGON_LOCATION);
    }
}
