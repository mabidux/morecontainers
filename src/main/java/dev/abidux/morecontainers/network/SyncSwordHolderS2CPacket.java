package dev.abidux.morecontainers.network;

import dev.abidux.morecontainers.block.custom.entity.SwordHolderBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSwordHolderS2CPacket {

    private final String dimensionId;
    private final BlockPos pos;
    private final ItemStack sword;
    public SyncSwordHolderS2CPacket(String dimensionId, BlockPos pos, ItemStack sword) {
        this.dimensionId = dimensionId;
        this.pos = pos;
        this.sword = sword;
    }

    public SyncSwordHolderS2CPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readBlockPos(), buf.readItem());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(dimensionId);
        buf.writeBlockPos(pos);
        buf.writeItem(sword);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            Level level = player.level();
            if (!level.dimension().location().toString().equals(dimensionId)) return;
            if (level.getBlockEntity(pos) instanceof SwordHolderBlockEntity entity) {
                entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    ((IItemHandlerModifiable)handler).setStackInSlot(0, sword);
                });
            }
        });
        return true;
    }

}