package dev.abidux.morecontainers.slot;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SpecificSlot extends SlotItemHandler {
    private final Item specificItem;
    public SpecificSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item specificItem) {
        super(itemHandler, index, xPosition, yPosition);
        this.specificItem = specificItem;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.is(specificItem);
    }
}
