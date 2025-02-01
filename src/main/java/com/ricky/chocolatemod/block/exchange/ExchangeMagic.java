package com.ricky.chocolatemod.block.exchange;

import com.ricky.chocolatemod.block.entity.ExchangeMagicBlockEntity;
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

public class ExchangeMagic extends Block implements EntityBlock {
    public ExchangeMagic(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && ChocolateCounter.getInstance().useChocolate(1000)) {
            // プレイヤーにアイテムを渡す
            ItemStack magic = new ItemStack(ModItems.MAGIC.get());
            if (!player.getInventory().add(magic)) {
                player.drop(magic, false); // インベントリがいっぱいならドロップ
            }
            return InteractionResult.SUCCESS;
        }
        if (level.isClientSide && ChocolateCounter.getInstance().getChocolate()>=1000){
            // クライアント側でパーティクルを表示
            spawnHeartParticles(level, pos);
        }
        return InteractionResult.CONSUME; // クライアント側での効果
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new ExchangeMagicBlockEntity(blockPos, state);
    }
    private void spawnHeartParticles(Level level, BlockPos pos) {
        if (level.isClientSide) {
            for (int i = 0; i < 5; i++) { // 5つのハートパーティクル
                double x = pos.getX() + 0.5 + (Math.random() - 0.5) * 0.7;
                double y = pos.getY() + 1.8;
                double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * 0.7;
                level.addParticle(net.minecraft.core.particles.ParticleTypes.HEART, x, y, z, 0, 0, 0);
            }
        }
    }
}
