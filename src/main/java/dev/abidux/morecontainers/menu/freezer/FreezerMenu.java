package dev.abidux.morecontainers.menu.freezer;

import dev.abidux.morecontainers.block.ModBlocks;
import dev.abidux.morecontainers.block.custom.entity.FreezerBlockEntity;
import dev.abidux.morecontainers.block.custom.entity.OvenBlockEntity;
import dev.abidux.morecontainers.menu.ModMenuTypes;
import dev.abidux.morecontainers.slot.FoodSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class FreezerMenu extends AbstractContainerMenu {
    public final FreezerBlockEntity blockEntity;
    public FreezerMenu(int pContainerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(pContainerId, playerInventory, (FreezerBlockEntity)playerInventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public FreezerMenu(int containerId, Inventory playerInventory, FreezerBlockEntity blockEntity) {
        super(ModMenuTypes.FREEZER.get(), containerId);
        this.blockEntity = blockEntity;

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addSlots();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = getSlot(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        if (index < 36) {
            if (!moveItemStackTo(stack, 36, 45, false)) return ItemStack.EMPTY;
        } else if (!moveItemStackTo(stack, 0, 36, false)) return ItemStack.EMPTY;
        if (stack.getCount() == 0) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        slot.onTake(player, stack);
        return stack.copy();
    }

    private void addSlots() {
        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    addSlot(new FoodSlot(handler, i+j*3, 62+18*i, 17+18*j));
                }
            }
        });
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 8 + i*18, 142));
        }
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 9; i++) {
                addSlot(new Slot(inventory, j*9+i+9, 8+i*18, 84+j*18));
            }
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.level(), blockEntity.getBlockPos()), pPlayer, ModBlocks.FREEZER.get());
    }
}