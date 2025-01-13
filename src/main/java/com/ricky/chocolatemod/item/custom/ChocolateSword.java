package com.ricky.chocolatemod.item.custom;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class ChocolateSword extends SwordItem {
    public ChocolateSword() {
        super(Tiers.DIAMOND, 5, -2.4f, new Properties().stacksTo(1));
    }
}
