package com.ricky.xp_giant_mod.event;

import com.ricky.xp_giant_mod.ScaleManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "xp_giant_mod")
public class ChargeJumpHandler {
    // クリーパーが潰れた状態の記録 (タイムスタンプを保持)
    private static Map<LivingEntity, Long> flattenedMobs = new HashMap<>();
    // 着地地点を追跡するための変数
    public static double landingX = 0;
    public static double landingY = 0;
    public static double landingZ = 0;

    // 10秒間潰れた状態を維持
    private static final long FLATTEN_DURATION = 10000L;  // 10秒

    //jump meter
    private static ServerBossEvent bossBar = new ServerBossEvent(
            Component.translatable("bar.charge_jump"), // ボスバーの名前
            BossEvent.BossBarColor.BLUE, // ボスバーの色
            BossEvent.BossBarOverlay.PROGRESS // ボスバーのオーバーレイ
    );

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
                // 着地地点を保存
                landingX = player.getX();
                landingY = player.getY();
                landingZ = player.getZ();
            }
            if (player.fallDistance > 0) {
                //着地時に範囲破壊
                if(player.experienceLevel>=60){
                    BlockPos pos = player.getOnPos();
                    Level level = player.level();
                    for (int x = -40; x <= 40; x++) {
                        for (int y = -25; y <= 25; y++) {
                            for (int z = -40; z <= 40; z++) {
                                BlockPos offsetPos = pos.offset(x, y, z);
                                BlockState state = level.getBlockState(offsetPos);
                                // 周囲のブロックが破壊可能なものであれば、破壊処理を行う
                                if (!state.isAir() && state.getBlock() != Blocks.BEDROCK) {
                                    Block block = state.getBlock();
                                    if (block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE || block == Blocks.DIAMOND_ORE ||
                                            block == Blocks.COAL_ORE || block == Blocks.LAPIS_ORE || block == Blocks.EMERALD_ORE ||
                                            block == Blocks.REDSTONE_ORE || block == Blocks.NETHER_QUARTZ_ORE || block == Blocks.ANCIENT_DEBRIS
                                            ||block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_GOLD_ORE ||
                                            block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_COAL_ORE ||
                                            block == Blocks.DEEPSLATE_LAPIS_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE ||
                                            block == Blocks.DEEPSLATE_REDSTONE_ORE) {
                                        level.destroyBlock(offsetPos, true, player);
                                    }else{
                                        level.destroyBlock(offsetPos, false, player);
                                    }
                                }
                            }
                        }
                    }
                }
                event.setCanceled(true); // 落下ダメージ無効化
                player.fallDistance = 0.0f; // 落下ダメージ無効化後、落下距離をリセット
                if (player.onGround()){
                    finish = true;
                }
            }
        }
    }


    // モブを潰れた状態に設定
    public static void setMobFlattened(LivingEntity mob) {
        flattenedMobs.put(mob, System.currentTimeMillis());
    }
    // モブが潰れた状態かを確認
    public static boolean isMobFlattened(LivingEntity mob) {
        if (flattenedMobs.containsKey(mob)) {
            long elapsedTime = System.currentTimeMillis() - flattenedMobs.get(mob);
            if (elapsedTime <= FLATTEN_DURATION) {
                return true;
            } else {
                flattenedMobs.remove(mob); // 時間経過で解除
                return false;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // サーバーサイドでのみ動作
        if (player.level().isClientSide || !(player instanceof ServerPlayer serverPlayer)) return;

        // プレイヤーがボスバーに追加されているかを確認
        boolean isPlayerInBossBar = bossBar.getPlayers().contains(serverPlayer);

        // スニーク状態を検出
        if (serverPlayer.isCrouching()) {
            if (!isPlayerInBossBar && player.experienceLevel >= 1) {
                bossBar.addPlayer(serverPlayer); // プレイヤーにボスバーを表示
            }

            chargeTime++;

            float progress = (float) chargeTime / MAX_CHARGE_TIME;
            bossBar.setProgress(progress);

            if (chargeTime >= MAX_CHARGE_TIME) {
                canChargeJump = true; // 最大チャージに達した
            }
        } else {
            // チャージが終了したらボスバーを非表示にし、チャージをリセット
            if (isPlayerInBossBar) {
                bossBar.removePlayer(serverPlayer);
            }
            chargeTime = 0;
            canChargeJump = false;
        }
    }


    private static void createExplosionEffect(LivingEntity attacker) {
        // 共通クラスを使用してスケールを取得
        Player player = (Player) attacker;
        float scale = ScaleManager.getScaleForPlayer(player);
        // 爆風の範囲を設定
        float explosionRadius = 5.0f*scale;  // 半径はfloat型にする
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
            if(player.experienceLevel>=20){
                ChargeJumpHandler.setMobFlattened(entity); // モブを潰れた状態に設定
            }
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
