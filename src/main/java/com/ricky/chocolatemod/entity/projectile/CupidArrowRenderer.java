package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CupidArrowRenderer extends ArrowRenderer<CupidArrowEntity> {
    public CupidArrowRenderer(EntityRendererProvider.Context context) {
        super(context); // EntityRendererProvider.Context を渡す
    }

    @Override
    public ResourceLocation getTextureLocation(CupidArrowEntity entity) {
        return new ResourceLocation(ChocolateMod.MOD_ID, "textures/entity/cupid_arrow.png"); // テクスチャのパスを指定
    }
}
