package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.item.ModFoods;
import com.ricky.chocolatemod.util.ChocolateCounter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldenApple extends Item {
    public GoldenApple() {
        super(new Properties().food(ModFoods.APPLE)); // 食べ物のプロパティを設定
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // 満腹度を無視して食べることを許可
        player.startUsingItem(hand); // アイテム使用開始
        return InteractionResultHolder.pass(player.getItemInHand(hand)); // 使用可能
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            // 金リンゴを初めて食べたフラグを設定
            if (!player.getPersistentData().getBoolean("ate_golden_apple")) {
                player.getPersistentData().putBoolean("ate_golden_apple", true);
            }
            ChocolateCounter.getInstance().addChocolate(1000);

            // **金リンゴのバフ効果を適用**
            if (!level.isClientSide) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1)); // 10秒間（200tick）再生Lv1
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0)); // 5分間 耐性Lv1
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0)); // 2分間 吸収Lv1
            }
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
