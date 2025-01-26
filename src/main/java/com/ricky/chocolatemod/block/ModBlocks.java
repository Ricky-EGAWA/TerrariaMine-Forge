package com.ricky.chocolatemod.block;

import com.ricky.chocolatemod.ChocolateMod;
import com.ricky.chocolatemod.block.entity.*;
import com.ricky.chocolatemod.block.exchange.*;
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

    //Exchange block
    public static final RegistryObject<Block> EXCHANGE_ORE_PICKAXE = registerBlock("exchange_ore_pickaxe",
            () -> new ExchangeOrePickaxe(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeOrePickaxeBlockEntity>> EXCHANGE_ORE_PICKAXE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_ore_pickaxe",
                    () -> BlockEntityType.Builder.of(ExchangeOrePickaxeBlockEntity::new, ModBlocks.EXCHANGE_ORE_PICKAXE.get()).build(null));

    public static final RegistryObject<Block> EXCHANGE_HEALER = registerBlock("exchange_healer",
            () -> new ExchangeHealer(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeHealerBlockEntity>> EXCHANGE_HEALER_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_healer",
                    () -> BlockEntityType.Builder.of(ExchangeHealerBlockEntity::new, ModBlocks.EXCHANGE_HEALER.get()).build(null));
    public static final RegistryObject<Block> EXCHANGE_HURRICANE = registerBlock("exchange_hurricane",
            () -> new ExchangeHurricane(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeHurricaneBlockEntity>> EXCHANGE_HURRICANE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_hurricane",
                    () -> BlockEntityType.Builder.of(ExchangeHurricaneBlockEntity::new, ModBlocks.EXCHANGE_HURRICANE.get()).build(null));
    public static final RegistryObject<Block> EXCHANGE_BOMB = registerBlock("exchange_bomb",
            () -> new ExchangeBomb(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeBombBlockEntity>> EXCHANGE_BOMB_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_bomb",
                    () -> BlockEntityType.Builder.of(ExchangeBombBlockEntity::new, ModBlocks.EXCHANGE_BOMB.get()).build(null));
    public static final RegistryObject<Block> EXCHANGE_SWORD = registerBlock("exchange_sword",
            () -> new ExchangeSword(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeSwordBlockEntity>> EXCHANGE_SWORD_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_sword",
                    () -> BlockEntityType.Builder.of(ExchangeSwordBlockEntity::new, ModBlocks.EXCHANGE_SWORD.get()).build(null));
    public static final RegistryObject<Block> EXCHANGE_SLINGSHOT = registerBlock("exchange_slingshot",
            () -> new ExchangeSlingshot(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeSlingshotBlockEntity>> EXCHANGE_SLINGSHOT_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_slingshot",
                    () -> BlockEntityType.Builder.of(ExchangeSlingshotBlockEntity::new, ModBlocks.EXCHANGE_SLINGSHOT.get()).build(null));
    public static final RegistryObject<Block> EXCHANGE_MAGIC = registerBlock("exchange_magic",
            () -> new ExchangeMagic(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BlockEntityType<ExchangeMagicBlockEntity>> EXCHANGE_MAGIC_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("exchange_magic",
                    () -> BlockEntityType.Builder.of(ExchangeMagicBlockEntity::new, ModBlocks.EXCHANGE_MAGIC.get()).build(null));


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
