package com.ricky.chocolatemod.util;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChocolateProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ChocolateCounter> CHOCOLATE_COUNTER = CapabilityManager
            .get(new CapabilityToken<ChocolateCounter>() { });
    private ChocolateCounter chocolate = null;
    private final LazyOptional<ChocolateCounter> optional = LazyOptional.of(this::createChocolateCounter);

    private ChocolateCounter createChocolateCounter() {
        if(this.chocolate == null) {
            this.chocolate = new ChocolateCounter();
        }
        return this.chocolate;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CHOCOLATE_COUNTER) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createChocolateCounter().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createChocolateCounter().loadNBTData(nbt);
    }
}
