package com.ricky.chocolatemod.entity.monster.goldman;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoldManRenderer extends MobRenderer<GoldManEntity, PlayerModel<GoldManEntity>> {

    public GoldManRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), false), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GoldManEntity entity) {
        return new ResourceLocation(ChocolateMod.MOD_ID, "textures/entity/gold_skin.png");
    }
}
