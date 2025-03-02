package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.entity.projectile.CustomRocketEntity;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomRocketItem extends Item {
    public CustomRocketItem(Properties properties) {
        super(properties);
    }
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!level.isClientSide) {
            System.out.println("boost");
            ItemStack itemstack = pContext.getItemInHand();
            Vec3 vec3 = pContext.getClickLocation();
            Direction direction = pContext.getClickedFace();
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, pContext.getPlayer(), vec3.x + (double)direction.getStepX() * 0.15D, vec3.y + (double)direction.getStepY() * 0.15D, vec3.z + (double)direction.getStepZ() * 0.15D, itemstack);
            level.addFreshEntity(fireworkrocketentity);
            itemstack.shrink(1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isFallFlying()) { // 地上で右クリック時に発射
            System.out.println("shoot rocket");
            if (!level.isClientSide) {
                CustomRocketEntity rocket = new CustomRocketEntity(level, player);
                rocket.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(rocket);
            }
            return InteractionResultHolder.success(stack);
        }else{
            ItemStack itemstack = player.getItemInHand(hand);
            if (!level.isClientSide) {
                FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, itemstack, player);
                level.addFreshEntity(fireworkrocketentity);

                player.awardStat(Stats.ITEM_USED.get(this));
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // 翻訳可能なテキストをツールチップに追加
        tooltip.add(Component.translatable("item.chocolatemod.rocket.tooltip"));
    }
}
