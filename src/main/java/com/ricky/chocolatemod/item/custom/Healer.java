package com.ricky.chocolatemod.item.custom;

import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

public class Healer extends Item {
    private BlockPos lastTarget;

    public Healer(Properties pProperties) {
        super(pProperties);
        MinecraftForge.EVENT_BUS.register(this);  // イベントを登録
    }

    //TODO ビームの描画
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide()) {
            Vec3 start = pPlayer.getEyePosition();
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
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }
}
