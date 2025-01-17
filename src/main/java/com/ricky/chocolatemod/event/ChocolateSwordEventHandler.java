package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.item.ModItems;
import com.ricky.chocolatemod.util.ChangeChocolate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChocolateSwordEventHandler {
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.getItem() == ModItems.CHOCOLATE_SWORD.get()) {
            Level level = player.level();
            Vec3 direction = player.getLookAngle();
            BlockPos startPos = player.blockPosition();
            System.out.println("attack");

            for (int i = 1; i <= 10; i++) {
                for (int x = -2; x <= 2; x++) {
                    BlockPos targetPos = startPos.offset((int)(direction.x * i + x), (int)(direction.y * i), (int)(direction.z * i));
                    ChangeChocolate.change(level, targetPos, true);
                    BlockPos targetPos2 = startPos.offset((int)(direction.x * i + x), (int)(direction.y * i)+1, (int)(direction.z * i));
                    ChangeChocolate.change(level, targetPos2, true);
                }
            }
        }
    }
}
