package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.monster.CrowedMonster;
import com.ricky.chocolatemod.entity.monster.SugarSlime;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpawnControlEvents {
    @SubscribeEvent
    public static void onCheckSpawn(MobSpawnEvent event) {
        if (event.getLevel() instanceof Level level) {
            // チョコレート次元にスポーンしないようにする
            if (level.dimension().location().toString().equals("chocolatemod:chocolate_dimension")) {
                event.setResult(MobSpawnEvent.Result.DENY);
                return;
            }
        }
        // スポーンしようとしているエンティティがモンスターカテゴリに属しているかを確認
        if (event.getEntity().getType().getCategory() == MobCategory.MONSTER) {
            if (event.getEntity().getType() == ModEntities.CROWED_MONSTER.get()){
                int count = event.getEntity().level().getEntitiesOfClass(CrowedMonster.class, event.getEntity().getBoundingBox().inflate(50)).size();
                if (event.getLevel() instanceof Level level) {
                    // エンドにスポーンしないようにする
                    if (!level.dimension().location().toString().equals("minecraft:overworld")) {
                        event.setResult(MobSpawnEvent.Result.DENY);
                        return;
                    }
                }
                // 一定数以上の場合、スポーンをキャンセル
                if (count >= 10 || event.getEntity().getType() == EntityType.ZOMBIE) {
                    event.setResult(MobSpawnEvent.Result.DENY);
                    event.setResult(MobSpawnEvent.Result.DENY);
                } else{
                    return;
                }
            }


            // エンティティが SugarSlime でない場合、スポーンをキャンセル
            event.setResult(MobSpawnEvent.Result.DENY);
        }
    }
}
