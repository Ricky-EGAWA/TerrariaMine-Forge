package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.monster.CrowedMonster;
import com.ricky.chocolatemod.entity.monster.SugarSlime;
import net.minecraft.server.level.ServerPlayer;
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
            // ワールドにいるSugarSlimeの数を取得
            int count = event.getEntity().level().getEntitiesOfClass(SugarSlime.class, event.getEntity().getBoundingBox().inflate(50)).size();
            // 一定数以上の場合、スポーンをキャンセル
            if (count >= 4) {
                event.setResult(MobSpawnEvent.Result.DENY);
            } else{
                return;
            }

            if (event.getEntity().getType() != ModEntities.CROWED_MONSTER.get()){
                // ワールド内のプレイヤーを取得
                boolean allowSpawn = event.getEntity().level().players().stream().anyMatch(player -> {
                    // FakePlayerを無視し、プレイヤーのカスタムデータを確認
                    if (!(player instanceof ServerPlayer) || player instanceof FakePlayer) {
                        return false;
                    }
                    // "crafted_chocolate_message_shown"がfalseのプレイヤーがいるか
                    return player.getPersistentData().getBoolean("crafted_chocolate_message_shown");
                });
                if(allowSpawn) {
                    count = event.getEntity().level().getEntitiesOfClass(CrowedMonster.class, event.getEntity().getBoundingBox().inflate(50)).size();
                    // 一定数以上の場合、スポーンをキャンセル
                    if (count >= 4) {
                        event.setResult(MobSpawnEvent.Result.DENY);
                    } else{
                        return;
                    }
                }else{
                    event.setResult(MobSpawnEvent.Result.DENY);
                }
            }


            // エンティティが SugarSlime でない場合、スポーンをキャンセル
            event.setResult(MobSpawnEvent.Result.DENY);
        }
    }
}
