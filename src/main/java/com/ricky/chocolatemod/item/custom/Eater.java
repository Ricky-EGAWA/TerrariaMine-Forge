package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.item.ModFoods;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Eater extends Item {
    public Eater() {
        super(new Item.Properties().food(ModFoods.EATER)); // 食べ物のプロパティを設定
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // 満腹度を無視して食べることを許可
        player.startUsingItem(hand); // アイテム使用開始
        return InteractionResultHolder.pass(player.getItemInHand(hand)); // 使用可能
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            BlockPos playerPos = player.blockPosition();

            // **半径3ブロック以内のブロックを金ブロックに変更**
            int radius = 3;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos nearbyPos = playerPos.offset(x, y, z);
                        ChangeChocolate.change(level, nearbyPos, false);
                    }
                }
            }


            Explosion explosion = level.explode(entity, playerPos.getX(), playerPos.getY(), playerPos.getZ(), 3.0F, Level.ExplosionInteraction.MOB);
            explosion.clearToBlow(); // プレイヤーにはダメージを与えない

            // **爆発エフェクトを発生（ダメージなし）**
            level.explode(null, player.getX(), player.getY(), player.getZ(), 0.0F, Level.ExplosionInteraction.NONE);

            // **爆発サウンドを再生**
            level.playSound(null, playerPos, SoundEvents.GENERIC_EXPLODE, player.getSoundSource(), 1.0F, 1.0F);
        }

        return stack;
    }
    @Override
    public boolean isEdible() {
        return true;  // 食べられることを確認する
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // 翻訳可能なテキストをツールチップに追加
        tooltip.add(Component.translatable("item.chocolatemod.eat.tooltip"));
    }

}

