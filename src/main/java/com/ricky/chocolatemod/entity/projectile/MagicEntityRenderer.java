package com.ricky.chocolatemod.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MagicEntityRenderer extends EntityRenderer<Entity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ChocolateMod.MOD_ID, "textures/entity/star.png");

    public MagicEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        // エンティティの描画位置・スケール調整
        poseStack.translate(0.0D, 0.2D, 0.0D); // 中心位置を調整
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation()); // カメラ方向に向ける
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F)); // 上下反転修正
        float scale = 0.5F; // スケール調整
        poseStack.scale(scale, scale, scale);

        // テクスチャをバインドして描画
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RENDER_TYPE);
        drawQuad(poseStack, vertexConsumer, packedLight);

        poseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return TEXTURE;
    }

    // 描画用のカスタムレンダータイプ
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE);

    // 四角形を描画するメソッド
    private void drawQuad(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
        PoseStack.Pose entry = poseStack.last();
        vertexConsumer.vertex(entry.pose(), -0.5F, -0.5F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F).overlayCoords(0).uv2(packedLight).normal(entry.normal(), 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(entry.pose(), 0.5F, -0.5F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F).overlayCoords(0).uv2(packedLight).normal(entry.normal(), 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(entry.pose(), 0.5F, 0.5F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F).overlayCoords(0).uv2(packedLight).normal(entry.normal(), 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(entry.pose(), -0.5F, 0.5F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F).overlayCoords(0).uv2(packedLight).normal(entry.normal(), 0.0F, 0.0F, 1.0F).endVertex();
    }
}
