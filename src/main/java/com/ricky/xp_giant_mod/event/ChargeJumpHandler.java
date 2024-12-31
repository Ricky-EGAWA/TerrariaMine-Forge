package com.ricky.xp_giant_mod.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "xp_giant_mod")
public class ChargeJumpHandler {

    private static final int MAX_CHARGE_TIME = 100; // 最大チャージ時間（例：5秒）

    private static int chargeTime = 0;
    public static boolean canChargeJump = false;
    public static boolean isSuperJumping = false;
    public static boolean finish = false;
    public static void setSuperJumping(boolean value) {
        isSuperJumping = value;
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player && isSuperJumping) {
            if (finish){
                isSuperJumping = false; // 着地後にスーパージャンプを終了
                finish = false;
                //爆風
                // 爆風の効果を発生させる
                createExplosionEffect(player);
            }
            System.out.println("Super Jump fall detected!");
            if (player.fallDistance > 0) {
                event.setCanceled(true); // 落下ダメージ無効化
                player.fallDistance = 0.0f; // 落下ダメージ無効化後、落下距離をリセット
                if (player.onGround()){
                    finish = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;


        // クライアントサイドのみで動作
        if (event.phase != TickEvent.Phase.START || !(player instanceof LocalPlayer)) return;

        LocalPlayer localPlayer = (LocalPlayer) player;

        // スニーク状態を検出
        if (localPlayer.isCrouching()) { // スニーク中のチャージ
            chargeTime++;
            if (chargeTime >= MAX_CHARGE_TIME) {
                canChargeJump = true; // 最大チャージに達した
            }
        } else {
            chargeTime = 0;
            canChargeJump = false;
        }
    }
    private static void createExplosionEffect(LivingEntity attacker) {
        // 爆風の範囲を設定
        float explosionRadius = 3.0f;  // 半径はfloat型にする
        AABB explosionArea = new AABB(
                attacker.getX() - explosionRadius, attacker.getY() - explosionRadius, attacker.getZ() - explosionRadius,
                attacker.getX() + explosionRadius, attacker.getY() + explosionRadius, attacker.getZ() + explosionRadius
        );

        // 範囲内のエンティティを取得
        var level = attacker.level();
        var entities = level.getEntitiesOfClass(LivingEntity.class, explosionArea, e -> e != attacker);

        // DamageSources インスタンスを取得
        DamageSources damageSources = level.damageSources();
        // 各エンティティにダメージとノックバックを適用
        for (LivingEntity entity : entities) {
            // ダメージを適用
            DamageSource damageSource = damageSources.magic();
            entity.hurt(damageSource, 4.0F);

            // ノックバック処理
            double knockbackStrength = 2.0;
            double dx = entity.getX() - attacker.getX();
            double dz = entity.getZ() - attacker.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance > 0.0) {
                dx /= distance;
                dz /= distance;
                entity.setDeltaMovement(entity.getDeltaMovement().add(dx * knockbackStrength, 0.5, dz * knockbackStrength));
            }
        }

        // 爆発のパーティクルを表示 (視覚効果のみ)
        level.explode(null, attacker.getX(), attacker.getY(), attacker.getZ(), 0.0F, Level.ExplosionInteraction.NONE);
    }


}
