package com.ricky.chocolatemod.util;

import net.minecraft.nbt.CompoundTag;

public class ChocolateCounter {
    private static final ChocolateCounter INSTANCE = new ChocolateCounter();
    private int chocolate;

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
}

