package com.ricky.chocolatemod.block;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.entity.ExchangeOrePickaxeBlockEntity;
import com.ricky.chocolatemod.block.exchange.ExchangeOrePickaxe;
import com.ricky.chocolatemod.block.milk.ModFluids;
import com.ricky.chocolatemod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChocolateMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChocolateMod.MOD_ID);

    // ブロック登録
    public static final RegistryObject<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> EXCHANGE_ORE_PICKAXE = registerBlock("exchange_ore_pickaxe",
            () -> new ExchangeOrePickaxe(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    // ブロックエンティティの登録
    public static final RegistryObject<BlockEntityType<ExchangeOrePickaxeBlockEntity>> EXCHANGE_ORE_PICKAXE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_ore_pickaxe",
                    () -> BlockEntityType.Builder.of(ExchangeOrePickaxeBlockEntity::new, ModBlocks.EXCHANGE_ORE_PICKAXE.get()).build(null));


    public static final RegistryObject<LiquidBlock> MILK_FLUID_BLOCK = BLOCKS.register("milk_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MILK, BlockBehaviour.Properties.copy(Blocks.WATER))
    );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
