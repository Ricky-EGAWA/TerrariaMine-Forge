package com.ricky.chocolatemod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoldenElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation WINGS_LOCATION = new ResourceLocation(ChocolateMod.MOD_ID,"textures/entity/golden_elytra.png");
   private final ElytraModel<T> elytraModel;

   public GoldenElytraLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
      super(pRenderer);
      this.elytraModel = new ElytraModel<>(pModelSet.bakeLayer(ModelLayers.ELYTRA));
   }

   public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      ItemStack itemstack = pLivingEntity.getItemBySlot(EquipmentSlot.CHEST);
      if (shouldRender(itemstack, pLivingEntity)) {
         ResourceLocation resourcelocation;
         if (pLivingEntity instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)pLivingEntity;
            if (abstractclientplayer.isElytraLoaded() && abstractclientplayer.getElytraTextureLocation() != null) {
               resourcelocation = abstractclientplayer.getElytraTextureLocation();
            } else if (abstractclientplayer.isCapeLoaded() && abstractclientplayer.getCloakTextureLocation() != null && abstractclientplayer.isModelPartShown(PlayerModelPart.CAPE)) {
               resourcelocation = abstractclientplayer.getCloakTextureLocation();
            } else {
               resourcelocation = getElytraTexture(itemstack, pLivingEntity);
            }
         } else {
            resourcelocation = getElytraTexture(itemstack, pLivingEntity);
         }

         pPoseStack.pushPose();
         pPoseStack.translate(0.0F, 0.0F, 0.125F);
         this.getParentModel().copyPropertiesTo(this.elytraModel);
         this.elytraModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
         VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(pBuffer, RenderType.armorCutoutNoCull(resourcelocation), false, itemstack.hasFoil());
         this.elytraModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
         pPoseStack.popPose();
      }
   }

   public boolean shouldRender(ItemStack stack, T entity) {
      return stack.getItem() == ModItems.GOLDEN_ELYTRA.get();
   }

   public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
      return WINGS_LOCATION;
   }
}
