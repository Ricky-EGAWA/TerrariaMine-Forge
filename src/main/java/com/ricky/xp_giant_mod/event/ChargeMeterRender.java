//package com.ricky.xp_giant_mod.event;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.client.event.RenderGameOverlayEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = "xp_giant_mod", value = Dist.CLIENT)
//public class ChargeMeterRenderer {
//
//    @SubscribeEvent
//    public static void onRenderHUD(RenderGameOverlayEvent event) {
//        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;
//
//        PoseStack poseStack = event.getMatrixStack();
//
//        // チャージメーターの描画
//        int chargePercentage = (int) ((ChargeJumpHandler.chargeTime / (float) ChargeJumpHandler.MAX_CHARGE_TIME) * 100);
//        if (ChargeJumpHandler.chargeTime > 0) {
//            event.getFontRenderer().draw(poseStack, "Charge: " + chargePercentage + "%", 10, 10, 0xFFFFFF);
//        }
//    }
//}
