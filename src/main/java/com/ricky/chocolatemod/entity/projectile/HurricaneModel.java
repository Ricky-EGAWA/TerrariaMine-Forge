package com.ricky.chocolatemod.entity.projectile;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class HurricaneModel<T extends HurricaneEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ChocolateMod.MOD_ID, "hurricane"), "main");
	private final ModelPart hurricane;

	public HurricaneModel(ModelPart root) {
		this.hurricane = root.getChild("hurricane");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hurricane = partdefinition.addOrReplaceChild("hurricane", CubeListBuilder.create().texOffs(40, 39).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 26).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-5.0F, -20.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -30.0F, -8.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(HurricaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		hurricane.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}