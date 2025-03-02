package com.ricky.chocolatemod.item.custom;

import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;

public class CustomElytraItem extends ElytraItem {
    public CustomElytraItem() {
        super(new Item.Properties().durability(432).fireResistant()); // 耐久値432（バニラと同じ）、耐火性を追加
    }
}
