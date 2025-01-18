package com.ricky.chocolatemod.entity.monster;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.wither.WitherBoss;

public class CrowedWitherRenderer extends WitherBossRenderer {
    private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_LOCATION = new ResourceLocation(ChocolateMod.MOD_ID,"textures/entity/crowed_wither.png");
    public CrowedWitherRenderer(EntityRendererProvider.Context p_174445_) {
        super(p_174445_);
    }
    public ResourceLocation getTextureLocation(WitherBoss pEntity) {
        int i = pEntity.getInvulnerableTicks();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? WITHER_INVULNERABLE_LOCATION : WITHER_LOCATION;
    }
}
