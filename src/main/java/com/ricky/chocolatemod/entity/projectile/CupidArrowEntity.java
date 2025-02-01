package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class CupidArrowEntity extends AbstractArrow {
    private int explosionRadius = 5;
    public CupidArrowEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }
    public CupidArrowEntity(Level pLevel, LivingEntity livingEntity){
        super(ModEntities.CUPID_ARROW.get(), livingEntity, pLevel);
    }

    @Override
    protected void tickDespawn() {
        // 独自の消滅条件を設定することもできます
        super.tickDespawn();
    }

    @Override
    public void tick(){
        super.tick();
        this.setNoGravity(true); // 重力を完全無効化
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    // カスタム処理を追加するメソッドもオーバーライドできます
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        Level level = this.level();
        if(level.isClientSide){
            return;
        }
        effect(pResult.getBlockPos());
        level.getEntities(this, this.getBoundingBox().inflate(explosionRadius), entity -> !(entity instanceof Player))
                .forEach(entity -> entity.hurt(this.damageSources().explosion(this, this.getOwner()), 100));
        this.discard();
    }
    @Override
    protected void onHitEntity(EntityHitResult pResult){
        Level level = this.level();
        if(level.isClientSide){
            return;
        }
        effect(pResult.getEntity().blockPosition());
        level.getEntities(this, this.getBoundingBox().inflate(explosionRadius), entity -> !(entity instanceof Player))
                .forEach(entity -> entity.hurt(this.damageSources().explosion(this, this.getOwner()), 100));
        this.discard();
    }
    private void effect(BlockPos impactPos){
        System.out.println("effect");
        System.out.println(this.level().isClientSide());
        // パーティクルの表示処理
        Level level = this.level();
        int particleCount = Math.min(explosionRadius * 20, 200); // パーティクルの量を調整
        for (int i = 0; i < particleCount; i++) {
            double offsetX = (Math.random() - 0.5) * explosionRadius * 2;
            double offsetY = (Math.random() - 0.5) * explosionRadius * 2;
            double offsetZ = (Math.random() - 0.5) * explosionRadius * 2;
            Vec3 pos = new Vec3(impactPos.getX() + offsetX,impactPos.getY() + offsetY,impactPos.getZ() + offsetZ);
            spawnParticleForAllPlayers(pos);
        }
        // **爆発音を再生**
        level.playLocalSound(
                impactPos.getX() + 0.5, // サウンドのX座標
                impactPos.getY() + 0.5, // サウンドのY座標
                impactPos.getZ() + 0.5, // サウンドのZ座標
                net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, // 爆発音
                net.minecraft.sounds.SoundSource.BLOCKS, // サウンドソース
                2.0F, // 音量
                1.0F, // ピッチ
                false // 距離減衰の適用
        );
    }

    public void spawnParticleForAllPlayers(Vec3 position) {
        // プレイヤーリストを取得
        MinecraftServer server = this.getServer();

        // プレイヤーリスト内のすべてのプレイヤーに対してパーティクルを送信
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            // プレイヤーにハートのパーティクルを送信
            ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(
                    ParticleTypes.HEART, // パーティクルのタイプ
                    true, // Always show the particle, even if out of the player's view
                    position.x, // x座標
                    position.y, // y座標
                    position.z, // z座標
                    0f, // X方向のオフセット（速度）
                    0f, // Y方向のオフセット（速度）
                    0f, // Z方向のオフセット（速度）
                    1,
                    1 // パーティクルの個数
            );

            // プレイヤーにパーティクルを送信
            player.connection.send(packet);
        }
    }
}