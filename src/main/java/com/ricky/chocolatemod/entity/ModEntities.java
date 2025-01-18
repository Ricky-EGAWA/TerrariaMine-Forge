package com.ricky.chocolatemod.entity;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.monster.CrowedMonster;
import com.ricky.chocolatemod.entity.monster.SugarSlime;
import com.ricky.chocolatemod.entity.projectile.BombEntity;
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
            ENTITY_TYPES.register("slingshot", () -> EntityType.Builder.<MyArrowEntity>of(MyArrowEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("slingshot"));

    public static final RegistryObject<EntityType<BombEntity>> BOMB =
            ENTITY_TYPES.register("bomb", () -> EntityType.Builder.<BombEntity>of(BombEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("bomb"));

    public static final RegistryObject<EntityType<SugarSlime>> SUGAR_SLIME =
            ENTITY_TYPES.register("sugar_slime", () -> EntityType.Builder.of(SugarSlime::new, MobCategory.MONSTER)
                    .sized(2.04F, 2.04F).build("sugar_slime"));

    public static final RegistryObject<EntityType<CrowedMonster>> CROWED_MONSTER =
            ENTITY_TYPES.register("crowed_monster", () -> EntityType.Builder.of(CrowedMonster::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f).build("crowed_monster"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}