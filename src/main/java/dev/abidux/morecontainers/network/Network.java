package dev.abidux.morecontainers.network;

import dev.abidux.morecontainers.MoreContainers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {

    private static SimpleChannel INSTANCE;
    private static int currentId;
    private static int id() {
        return currentId++;
    }

    public static void registerPackets() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MoreContainers.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(v -> true)
                .serverAcceptedVersions(v -> true)
                .simpleChannel();

        INSTANCE.messageBuilder(SyncSwordHolderS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncSwordHolderS2CPacket::new)
                .encoder(SyncSwordHolderS2CPacket::toBytes)
                .consumerNetworkThread(SyncSwordHolderS2CPacket::handle)
                .add();
    }

    public static <P> void sendToServer(P packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <P> void sendToClient(ServerPlayer player, P packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static <P> void sendToClients(P packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

}