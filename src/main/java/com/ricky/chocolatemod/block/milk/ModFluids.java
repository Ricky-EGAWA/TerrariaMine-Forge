package com.ricky.chocolatemod.block.milk;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.ModBlocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, ChocolateMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_MILK = FLUIDS.register("milk_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MILK_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MILK = FLUIDS.register("flowing_milk",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MILK_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MILK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MILK_FLUID_TYPE, SOURCE_MILK, FLOWING_MILK)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.MILK_FLUID_BLOCK)
            .bucket(() -> Items.MILK_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
