package com.ricky.chocolatemod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ChocolateCounter {
    private static final ChocolateCounter INSTANCE = new ChocolateCounter();
    private int chocolate;
    private int shoot_count = 0;

    // プライベートコンストラクタ（直接のインスタンス生成を防ぐ）
    public ChocolateCounter() {}

    // シングルトンインスタンスへのアクセスメソッド
    public static ChocolateCounter getInstance() {
        return INSTANCE;
    }

    public int getChocolate() {
        return chocolate;
    }

    public void addChocolate(int add) {
        this.chocolate += add;
    }

    public boolean useChocolate(int count) {
        if (this.chocolate >= count) {
            this.chocolate -= count;
            return true;
        } else {
            return false;
        }
    }

    public void copyFrom(ChocolateCounter source) {
        this.chocolate = source.chocolate;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("chocolate", chocolate);
    }

    public void loadNBTData(CompoundTag nbt) {
        chocolate = nbt.getInt("chocolate");
    }

    public void shootEvent(ServerPlayer player){
        shoot_count++;
        System.out.println(shoot_count);
        if (shoot_count == 8){
            player.getPersistentData().putBoolean("finish_shooting_event", true);
            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§6ナイスショット！")));
            player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e報酬5000ゴールド")));
            this.chocolate += 5000;
        }
    }
    public void netherEvent(){
        System.out.println("nether event");
        this.chocolate += 400000;
    }
}

