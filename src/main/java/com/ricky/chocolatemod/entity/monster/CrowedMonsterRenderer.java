package com.ricky.chocolatemod.entity.monster;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class CrowedMonsterRenderer extends ZombieRenderer {
    public CrowedMonsterRenderer(EntityRendererProvider.Context p_174456_) {
        super(p_174456_);
    }
    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation(ChocolateMod.MOD_ID,"textures/entity/crowed_monster.png");
    public ResourceLocation getTextureLocation(Zombie pEntity) {
        return ZOMBIE_LOCATION;
    }
}
