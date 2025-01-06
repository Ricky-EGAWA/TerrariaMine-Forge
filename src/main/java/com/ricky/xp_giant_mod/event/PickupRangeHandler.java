package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.ScaleManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class PickupRangeHandler {

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            float scale = ScaleManager.getScaleForPlayer(player); // スケールを取得
            double pickupRange = scale*2.5f;  // スケールに基づいて範囲を設定

            List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class,
                    player.getBoundingBox().inflate(pickupRange, pickupRange / 2, pickupRange));

            for (ItemEntity item : items) {
                if (!item.hasPickUpDelay() && !item.isRemoved()) {
                    item.playerTouch(player);  // プレイヤーがアイテムを拾う
                }
            }
        }
    }
}
