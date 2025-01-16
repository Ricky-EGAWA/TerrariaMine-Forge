package com.ricky.chocolatemod.entity;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.projectile.MyArrowEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChocolateMod.MOD_ID);

    public static final RegistryObject<EntityType<MyArrowEntity>> MY_ARROW =
            ENTITY_TYPES.register("dice_projectile", () -> EntityType.Builder.<MyArrowEntity>of(MyArrowEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("dice_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}