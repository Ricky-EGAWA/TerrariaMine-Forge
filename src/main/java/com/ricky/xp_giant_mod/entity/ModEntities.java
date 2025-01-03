package com.ricky.xp_giant_mod.entity;

import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.entity.custom.GiantZombie;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, XPGiantMod.MOD_ID);

    public static final RegistryObject<EntityType<GiantZombie>> GIANT_ZOMBIE =
            ENTITY_TYPES.register("giant_zombie", () -> EntityType.Builder.of(GiantZombie::new, MobCategory.CREATURE)
                    .sized(0.6f*25, 1.95f*25).build("giant_zombie"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
