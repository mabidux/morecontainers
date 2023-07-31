package dev.abidux.morecontainers.util;

import dev.abidux.morecontainers.block.custom.entity.IronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomContainerOpenersCounter<T extends RandomizableContainerBlockEntity> extends ContainerOpenersCounter {

    private final T t;
    public CustomContainerOpenersCounter(T t) {
        this.t = t;
    }

    protected void onOpen(Level level, BlockPos pos, BlockState state) {
        level.playSound(null, pos.getX()+.5f, pos.getY()+.5f, pos.getZ()+.5f, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, .5f, level.random.nextFloat()*.1f+.9f);
    }

    protected void onClose(Level level, BlockPos pos, BlockState state) {
        level.playSound(null, pos.getX()+.5f, pos.getY()+.5f, pos.getZ()+.5f, SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, .5f, level.random.nextFloat()*.1f+.9f);
    }

    protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155364_, int param) {
        level.blockEvent(pos, state.getBlock(), 1, param);
    }

    protected boolean isOwnContainer(Player player) {
        if (!(player.containerMenu instanceof ChestMenu)) {
            return false;
        }
        Container container = ((ChestMenu)player.containerMenu).getContainer();
        return container == t || container instanceof CompoundContainer && ((CompoundContainer)container).contains(t);
    }
}