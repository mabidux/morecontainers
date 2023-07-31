package dev.abidux.morecontainers.menu.toaster;

import dev.abidux.morecontainers.block.ModBlocks;
import dev.abidux.morecontainers.block.custom.entity.ToasterBlockEntity;
import dev.abidux.morecontainers.menu.ModMenuTypes;
import dev.abidux.morecontainers.slot.SpecificSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ToasterMenu extends AbstractContainerMenu {

    public final ToasterBlockEntity blockEntity;
    public ToasterMenu(int pContainerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(pContainerId, playerInventory, (ToasterBlockEntity)playerInventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public ToasterMenu(int pContainerId, Inventory playerInventory, ToasterBlockEntity blockEntity) {
        super(ModMenuTypes.TOASTER.get(), pContainerId);
        this.blockEntity = blockEntity;

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addSlots();
    }

    private void addSlots() {
        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            addSlot(createBreadSlot(handler, 0, 71, 35));
            addSlot(createBreadSlot(handler, 1, 89, 35));
        });
    }

    private Slot createBreadSlot(IItemHandler handler, int index, int x, int y) {
        return new SpecificSlot(handler, index, x, y, Items.BREAD);
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
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot slot = getSlot(pIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        if (pIndex < 36) {
            if (!moveItemStackTo(stack, 36, 38, false)) return ItemStack.EMPTY;
        } if (!moveItemStackTo(stack, 0, 36, false)) return ItemStack.EMPTY;
        if (stack.getCount() == 0) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        slot.onTake(pPlayer, stack);
        return stack.copy();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(pPlayer.level(), blockEntity.getBlockPos()), pPlayer, ModBlocks.TOASTER.get());
    }
}