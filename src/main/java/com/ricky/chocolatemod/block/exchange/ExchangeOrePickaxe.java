package com.ricky.chocolatemod.block.exchange;

import com.ricky.chocolatemod.block.entity.ExchangeOrePickaxeBlockEntity;
import com.ricky.chocolatemod.item.ModItems;
import com.ricky.chocolatemod.util.ChocolateCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ExchangeOrePickaxe extends Block implements EntityBlock {
    public ExchangeOrePickaxe(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && ChocolateCounter.getInstance().useChocolate(500)) {
            // プレイヤーにアイテムを渡す
            ItemStack orePickaxe = new ItemStack(ModItems.ORE_PICKAXE.get());
            if (!player.getInventory().add(orePickaxe)) {
                player.drop(orePickaxe, false); // インベントリがいっぱいならドロップ
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME; // クライアント側での効果
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new ExchangeOrePickaxeBlockEntity(blockPos, state);
    }
}
