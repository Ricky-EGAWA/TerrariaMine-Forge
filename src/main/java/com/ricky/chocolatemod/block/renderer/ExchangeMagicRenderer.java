package com.ricky.chocolatemod.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ricky.chocolatemod.block.entity.ExchangeMagicBlockEntity;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ExchangeMagicRenderer implements BlockEntityRenderer<ExchangeMagicBlockEntity> {
    private final ItemRenderer itemRenderer;
    private final Font font;
    private int lightAtPos = 0xF000F0; // 最大限に明るい光源値

    public ExchangeMagicRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.font = context.getFont();
    }

    @Override
    public void render(ExchangeMagicBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // アイテムを描画
        renderItem(blockEntity, partialTicks, poseStack, bufferSource, packedOverlay);

        // 文字を描画
        renderText(poseStack, bufferSource);
    }

    private void renderItem(ExchangeMagicBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedOverlay) {
        poseStack.pushPose();

        // ブロックの中心に移動
        poseStack.translate(0.5, 1.3, 0.5);
        poseStack.scale(1.5F, 1.5F, 1.5F); // スケールを調整

        // 回転を追加 (時間に応じて回転する)
        float angle = (blockEntity.getLevel().getGameTime() + partialTicks) % 360; // 回転角度
        poseStack.mulPose(Axis.YP.rotationDegrees(angle * 3)); // Y軸回転

        // アイテムを描画
        ItemStack itemStack = new ItemStack(ModItems.MAGIC.get());
        BakedModel model = itemRenderer.getModel(itemStack, null, null, 0);

        // 最大ライティングを強制
        itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, lightAtPos, packedOverlay, model);

        poseStack.popPose();
    }

    private void renderText(PoseStack poseStack, MultiBufferSource bufferSource) {
        poseStack.pushPose();

        // テキストを描画する文字列
        String text = "マジカルチョコステッキ";
        String cost = "1000";

        // 各方向に合わせた回転角度
        int[] rotations = {0, 90, 180, 270}; // 北 (0度), 東 (90度), 南 (180度), 西 (270度)

        for (int rotation : rotations) {
            poseStack.pushPose();

            // ブロックの中心から少し外側へ移動
            poseStack.translate(0.5, 0.9, 0.5); // ブロック中心
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation)); // 各面に合わせた回転
            poseStack.translate(0.0, 0.0, -0.581); // 面の少し手前に移動

            poseStack.mulPose(Axis.ZP.rotationDegrees(180));


            // スケールを調整
            float scale = 0.007f; // テキストを小さくする
            poseStack.scale(scale, scale, scale);

            // テキストの位置を調整 (中心揃え)
            poseStack.translate(-font.width(text) / 2.0f, 0, 0);

            // テキストを描画
            font.drawInBatch(
                    text,
                    0,
                    0,
                    0xEDCE09,
                    false,
                    poseStack.last().pose(),
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    lightAtPos
            );
            // スケールを調整
            float scale2 = 1.5f; // テキストを小さくする
            poseStack.scale(scale2, scale2, scale2);

            // テキストの位置を調整 (中心揃え)
            poseStack.translate(0, 12, 0); // 1行目の下に移動 (12ピクセル分)
            poseStack.translate(font.width(text) / 3.0f -font.width(cost) / 2.0f, 0, 0); // 2行目も中心揃え

            // テキストを描画
            font.drawInBatch(
                    cost,
                    0,
                    0,
                    0xFFFFFF, // 白色
                    false,
                    poseStack.last().pose(),
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    lightAtPos
            );

            poseStack.popPose(); // 次の面のためにリセット
        }

        poseStack.popPose();
    }

}
