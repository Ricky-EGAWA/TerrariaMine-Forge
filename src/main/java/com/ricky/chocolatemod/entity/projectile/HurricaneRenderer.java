package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class HurricaneRenderer extends EntityRenderer<HurricaneEntity> {
    private static final ResourceLocation TEXT = new ResourceLocation(ChocolateMod.MOD_ID,"textures/entity/hurricane_entity.png");

    public HurricaneRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(HurricaneEntity pEntity) {
        return TEXT;
    }
}
