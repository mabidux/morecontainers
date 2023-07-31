package dev.abidux.morecontainers.block.custom.entity;

import dev.abidux.morecontainers.block.ModBlockEntities;
import dev.abidux.morecontainers.util.CustomContainerOpenersCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class IronChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);
    private final CustomContainerOpenersCounter<IronChestBlockEntity> openersCounter = new CustomContainerOpenersCounter<>(this);
    public final ChestLidController chestLidController = new ChestLidController();

    public IronChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.IRON_CHEST.get(), pPos, pBlockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.morecontainers.iron_chest");
    }

    public static void lidAnimateTick(Level pLevel, BlockPos pPos, BlockState pState, IronChestBlockEntity pBlockEntity) {
        pBlockEntity.chestLidController.tickLid();
    }

    public boolean triggerEvent(int pId, int pType) {
        if (pId == 1) {
            this.chestLidController.shouldBeOpen(pType > 0);
            return true;
        }
        return super.triggerEvent(pId, pType);
    }

    public void startOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return ChestMenu.sixRows(pContainerId, pInventory, this);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    public float getOpenNess(float pPartialTicks) {
        return this.chestLidController.getOpenness(pPartialTicks);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    private LazyOptional<IItemHandlerModifiable> chestHandler;
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (chestHandler == null) chestHandler = LazyOptional.of(() -> new InvWrapper(this));
            return chestHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setBlockState(BlockState pBlockState) {
        super.setBlockState(pBlockState);
        if (this.chestHandler != null) {
            LazyOptional<?> oldHandler = this.chestHandler;
            this.chestHandler = null;
            oldHandler.invalidate();
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (chestHandler != null) {
            chestHandler.invalidate();
            chestHandler = null;
        }
    }
}