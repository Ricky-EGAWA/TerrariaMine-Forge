package com.ricky.chocolatemod.entity.projectile;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CustomRocketRenderer extends EntityRenderer<CustomRocketEntity> {
    public CustomRocketRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomRocketEntity entity) {
        return new ResourceLocation("chocolatemod", "textures/entity/custom_rocket.png");
    }
}
