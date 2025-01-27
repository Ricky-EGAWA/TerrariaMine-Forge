package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class Healer extends Item {
    private BlockPos lastTarget;

    public Healer(Properties pProperties) {
        super(pProperties);
        MinecraftForge.EVENT_BUS.register(this);  // イベントを登録
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {//TODO 手を振る動作の調整
        Vec3 start = pPlayer.getEyePosition();
        if (!pLevel.isClientSide()) {
            Vec3 look = pPlayer.getLookAngle().scale(50); // 50ブロック先まで
            BlockHitResult hitResult = pLevel.clip(new net.minecraft.world.level.ClipContext(
                    start, start.add(look), net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, pPlayer));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                this.lastTarget = hitResult.getBlockPos();
                int explosionRadius = 9;

                for (int x = -explosionRadius; x <= explosionRadius; x++) {
                    for (int y = -explosionRadius; y <= explosionRadius; y++) {
                        for (int z = -explosionRadius; z <= explosionRadius; z++) {
                            BlockPos nearbyPos = lastTarget.offset(x, y, z);
                            ChangeChocolate.change(pLevel, nearbyPos, true);
                        }
                    }
                }
            }
        }else{
            Vec3 look = pPlayer.getLookAngle().scale(50); // 50ブロック先まで
            BlockHitResult hitResult = pLevel.clip(new net.minecraft.world.level.ClipContext(
                    start, start.add(look), net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, pPlayer));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                this.lastTarget = hitResult.getBlockPos();
                // パーティクルを発生させる処理を追加
                spawnParticles(pLevel, start, lastTarget);
            }

        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }

    private void spawnParticles(Level level, Vec3 start, BlockPos target) {
        // 目標ブロックまでのベクトル
        Vec3 direction = new Vec3(target.getX() - start.x, target.getY() - start.y, target.getZ() - start.z);
        // 目標までの距離を求める
        double distance = direction.length();
        // 単位ベクトルを得る（方向を示す）
        direction = direction.normalize();

        // パーティクルの数（距離に比例させる）
        int particleCount = (int) (distance * 5); // 5パーティクル／ブロック

        // DustParticleOptionsを作成（茶色っぽい色）
        Vector3f color = new Vector3f(0.5f, 0.25f, 0.0f); // R:0.5, G:0.25, B:0.0 (茶色)
        float size = 3.0f; // パーティクルのサイズ

        DustParticleOptions dustOptions = new DustParticleOptions(color, size);

        // 線形にパーティクルを配置
        for (int i = 0; i < particleCount; i++) {
            // 開始位置から方向ベクトルを進めた位置を計算
            double x = start.x + direction.x * i;
            double y = start.y + direction.y * i;
            double z = start.z + direction.z * i;

            // 茶色いパーティクルを生成
            level.addParticle(
                    dustOptions,  // 使用するパーティクル
                    x, y, z,  // パーティクルの座標
                    0.0, 0.25, 0.0 // パーティクルの色（茶色っぽい色）
            );
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // 翻訳可能なテキストをツールチップに追加
        tooltip.add(Component.translatable("item.chocolatemod.healer.tooltip"));
    }
}
