package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.entity.monster.goldman.GoldManEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldStaff extends Item {

    public GoldStaff(Properties pProperties) {
        super(pProperties);
        MinecraftForge.EVENT_BUS.register(this);  // イベントを登録
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide) {
            GoldManEntity.spawnCustomPlayer((ServerLevel) pLevel, pPlayer.getOnPos().above());
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // 翻訳可能なテキストをツールチップに追加
        tooltip.add(Component.translatable("item.chocolatemod.healer.tooltip"));
    }
}
