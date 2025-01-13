package com.ricky.chocolatemod.block;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.milk.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChocolateMod.MOD_ID);
    public static final RegistryObject<LiquidBlock> MILK_FLUID_BLOCK = BLOCKS.register("milk_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MILK, BlockBehaviour.Properties.copy(Blocks.WATER))
    );


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
