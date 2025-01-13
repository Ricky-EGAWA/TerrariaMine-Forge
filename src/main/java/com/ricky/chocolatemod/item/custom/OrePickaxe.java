package com.ricky.chocolatemod.item.custom;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;

public class OrePickaxe extends PickaxeItem {
    public OrePickaxe() {
        super(Tiers.NETHERITE, 1, 2, new Properties().stacksTo(1));
    }
}
