package com.ricky.xp_giant_mod.entity;

import com.ricky.xp_giant_mod.XPGiantMod;
import com.ricky.xp_giant_mod.entity.custom.GiantMummy;
import com.ricky.xp_giant_mod.entity.custom.GiantRavager;
import com.ricky.xp_giant_mod.entity.custom.GiantVindicator;
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
    public static final RegistryObject<EntityType<GiantVindicator>> GIANT_VINDICATOR =
            ENTITY_TYPES.register("giant_vindicator", () -> EntityType.Builder.of(GiantVindicator::new, MobCategory.CREATURE)
                    .sized(0.6f*6, 1.95f*6).build("giant_vindicator"));
    public static final RegistryObject<EntityType<GiantRavager>> GIANT_RAVAGER =
            ENTITY_TYPES.register("giant_ravager", () -> EntityType.Builder.of(GiantRavager::new, MobCategory.CREATURE)
                    .sized(1.95f*12.5f, 2.2f*12.5f).build("giant_ravager"));
    public static final RegistryObject<EntityType<GiantMummy>> GIANT_MUMMY =
            ENTITY_TYPES.register("giant_mummy", () -> EntityType.Builder.of(GiantMummy::new, MobCategory.CREATURE)
                    .sized(0.6f*2.5f, 1.95f*2.5f).build("giant_mummy"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
