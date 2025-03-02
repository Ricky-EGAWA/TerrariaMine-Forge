package com.ricky.chocolatemod.entity.monster.fighter;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;

public class FighterEntity extends PiglinBrute {
    public FighterEntity(EntityType<? extends PiglinBrute> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
