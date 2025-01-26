package com.ricky.chocolatemod.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ricky.chocolatemod.block.entity.ExchangeOrePickaxeBlockEntity;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ExchangeOrePickaxeRenderer implements BlockEntityRenderer<ExchangeOrePickaxeBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ExchangeOrePickaxeRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ExchangeOrePickaxeBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        // ブロックの中心に移動
        poseStack.translate(0.5, 1.3, 0.5);
        poseStack.scale(1.5F, 1.5F, 1.5F); // スケールを調整

        // 回転を追加 (時間に応じて回転する)
        float angle = (blockEntity.getLevel().getGameTime() + partialTicks) % 360; // 回転角度
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(angle*3)); // Y軸回転

        // アイテムを描画
        ItemStack itemStack = new ItemStack(ModItems.ORE_PICKAXE.get());
        BakedModel model = itemRenderer.getModel(itemStack, null, null, 0);

        // 最大ライティングを強制
        int lightAtPos = 0xF000F0; // 最大限に明るい光源値
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, lightAtPos, packedOverlay, model);

        poseStack.popPose();
    }
}
