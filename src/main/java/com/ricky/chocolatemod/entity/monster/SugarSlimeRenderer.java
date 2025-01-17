package com.ricky.chocolatemod.entity.monster;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class SugarSlimeRenderer extends SlimeRenderer {
    private static final ResourceLocation SLIME_LOCATION = new ResourceLocation(ChocolateMod.MOD_ID,"textures/entity/sugar_slime.png");
    public SugarSlimeRenderer(EntityRendererProvider.Context p_174391_) {
        super(p_174391_);
    }
    public ResourceLocation getTextureLocation(Slime pEntity) {
        return SLIME_LOCATION;
    }
}
