package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MyArrowRenderer extends ArrowRenderer<MyArrowEntity> {//TODO パチンコ玉のような見た目にする
    public MyArrowRenderer(EntityRendererProvider.Context context) {
        super(context); // EntityRendererProvider.Context を渡す
    }

    @Override
    public ResourceLocation getTextureLocation(MyArrowEntity entity) {
        return new ResourceLocation(ChocolateMod.MOD_ID, "textures/entity/my_arrow.png"); // テクスチャのパスを指定
    }
}
