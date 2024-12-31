package com.ricky.xp_giant_mod.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityMixin {
    @Invoker("getBlockJumpFactor")
    float invokeGetBlockJumpFactor();
}
