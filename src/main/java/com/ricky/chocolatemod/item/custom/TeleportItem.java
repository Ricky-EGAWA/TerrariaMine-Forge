package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.ChocolateMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TeleportItem extends Item {
    public TeleportItem(Properties properties) {
        super(properties);
    }
    public BlockPos rePos;
    public ServerLevel reLevel;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        Level level = pPlayer.level();

        if (!level.isClientSide) {
            // プレイヤーがchocolate dimensionにいるか確認
            if (level.dimension().location().toString().equals("chocolatemod:chocolate_dimension")) {
                System.out.println(pPlayer.position());
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    if (reLevel != null) {
                        serverPlayer.teleportTo(reLevel, rePos.getX(), rePos.getY(), rePos.getZ(), pPlayer.getYRot(), pPlayer.getXRot());
                    }
                }
            }else{
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    rePos = pPlayer.getOnPos();
                    reLevel = serverPlayer.serverLevel();
                    ResourceKey<Level> customDimensionKey = ResourceKey.create(
                            Registries.DIMENSION, new ResourceLocation(ChocolateMod.MOD_ID, "chocolate_dimension")
                    );
                    ServerLevel chocolateLevel = serverPlayer.getServer().getLevel(customDimensionKey);
                    if (chocolateLevel != null) {
                        serverPlayer.teleportTo(chocolateLevel, 0.5, 100, 0.5, pPlayer.getYRot(), pPlayer.getXRot());
                    }
                }
            }
        }


        // アイテムの使用結果を返す
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}