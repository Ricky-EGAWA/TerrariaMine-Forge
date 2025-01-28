package com.ricky.chocolatemod.event;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.entity.ModEntities;
import com.ricky.chocolatemod.entity.monster.CrowedWither;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ChocolateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomStructureSpawner {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        // サーバサイドのみで処理
        if (event.getLevel().isClientSide()) return;

        if (event.getLevel() instanceof ServerLevel serverLevel) {
            // ディメンションをチェック
            if (serverLevel.dimension().location().toString().equals("chocolatemod:chocolate_dimension")) {
                // 生成済みか確認する
                BlockPos pos= new BlockPos(200, 100, 0);
                if (serverLevel.getBlockState(pos).is(Blocks.BEDROCK)) {
                    System.out.println("Skipping structure and wither generation.");
                    return;
                }
                // 構造物を配置する座標 (例: 0, 100, 0)
                BlockPos structurePosition = new BlockPos(0, 100, 0);

                // 構造物のResourceLocation (NBTファイルのパス)
                ResourceLocation structureLocation = new ResourceLocation(ChocolateMod.MOD_ID, "shop");

                // 構造物テンプレートを取得 (Optional)
                Optional<StructureTemplate> optionalTemplate = serverLevel.getStructureManager().get(structureLocation);

                if (optionalTemplate.isPresent()) {
                    // Optionalから構造物テンプレートを取得
                    StructureTemplate template = optionalTemplate.get();

                    // 構造物を指定位置に生成
                    template.placeInWorld(
                            serverLevel,                    // 対象のレベル (ワールド)
                            structurePosition,              // 配置座標
                            structurePosition,              // ミラーリングや回転なし
                            new StructurePlaceSettings(), // 配置設定
                            serverLevel.random,             // ランダム
                            2                               // フラグ (生成モード: 更新処理など)
                    );
                    System.out.println("Structure generated at " + structurePosition);
                } else {
                    System.err.println("Failed to load structure template: " + structureLocation);
                }

                System.out.println("place boss");
                // 岩盤で囲まれた空間を作成
                generateBedrockBox(serverLevel, new BlockPos(200, 100, 0), 50, 15, 50);

                // ウィザーを空間の中央にスポーン
                spawnWither(serverLevel, new BlockPos(225, 107, 25));
            }
        }
    }

    private static void generateBedrockBox(ServerLevel level, BlockPos origin, int width, int height, int depth) {
        BlockPos end = origin.offset(width, height, depth);

        for (int x = origin.getX(); x <= end.getX(); x++) {
            for (int y = origin.getY(); y <= end.getY(); y++) {
                for (int z = origin.getZ(); z <= end.getZ(); z++) {
                    // 外側だけを岩盤で囲む
                    if (x == origin.getX() || x == end.getX() ||
                            y == origin.getY() || y == end.getY() ||
                            z == origin.getZ() || z == end.getZ()) {
                        level.setBlock(new BlockPos(x, y, z), Blocks.BEDROCK.defaultBlockState(), 3);
                    } else {
                        // 内部を空気に設定
                        level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }

        System.out.println("Bedrock box generated from " + origin + " to " + end);
    }

    private static void spawnWither(ServerLevel level, BlockPos position) {
        CrowedWither wither = new CrowedWither(ModEntities.CROWED_WITHER.get(),level);
        wither.setPos(position.getX() + 0.5, position.getY(), position.getZ() + 0.5); // 中心座標に配置

        // ウィザーをワールドに追加
        level.addFreshEntity(wither);

        System.out.println("Wither spawned at " + position);
    }
}
