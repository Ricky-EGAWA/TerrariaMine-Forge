package com.ricky.chocolatemod.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin{
    @Inject(method = "swing", at = @At("HEAD"), cancellable = true)
    private void swingChocolateSword(InteractionHand pHand){

    }
}
