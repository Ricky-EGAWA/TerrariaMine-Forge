package com.ricky.chocolatemod.entity.projectile;

import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.util.ChangeChocolate;
import com.ricky.chocolatemod.util.ChocolateCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MyArrowEntity extends AbstractArrow {
    private Player player;
    public MyArrowEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }
    public MyArrowEntity(Level pLevel, LivingEntity livingEntity){
        super(ModEntities.MY_ARROW.get(), livingEntity, pLevel);
        player = (Player) livingEntity;
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
        BlockPos hitPos = pResult.getBlockPos();
        BlockState blockState = level.getBlockState(hitPos);
        if (!player.getPersistentData().getBoolean("finish_shooting_event")){
            // ターゲットブロックかどうか確認
            if (blockState.getBlock() == Blocks.TARGET) {
                // もしターゲットブロックならば、処理を行う
                System.out.println("Hit a Target Block at: " + hitPos);
                ChocolateCounter.getInstance().shootEvent((ServerPlayer) player);
                level.setBlock(hitPos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);
                this.discard();
            }
        } else{
            for (int x = -3; x <= 3; x++) {
                for (int y = -3; y <= 3; y++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos nearbyPos = hitPos.offset(x, y, z);
                        ChangeChocolate.change(this.level(), nearbyPos, false,2);
                    }
                }
            }
            this.discard();
        }
    }
    @Override
    protected void onHitEntity(EntityHitResult pResult){
        Entity entity = pResult.getEntity();
        DamageSource damagesource = this.damageSources().arrow(this, this.getOwner());
        entity.hurt(damagesource, 8);
        BlockPos blockPos = entity.getOnPos();
        // 範囲内のすべてのブロックに対して処理を行う
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos nearbyPos = blockPos.offset(x, y, z);
                    ChangeChocolate.change(this.level(), nearbyPos, false);
                }
            }
        }
        this.discard();
    }
}