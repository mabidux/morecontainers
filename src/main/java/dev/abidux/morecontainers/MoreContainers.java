package dev.abidux.morecontainers;

import dev.abidux.morecontainers.block.ModBlockEntities;
import dev.abidux.morecontainers.block.ModBlocks;
import dev.abidux.morecontainers.item.ModCreativeTabs;
import dev.abidux.morecontainers.item.ModItems;
import dev.abidux.morecontainers.menu.ModMenuTypes;
import dev.abidux.morecontainers.menu.freezer.FreezerScreen;
import dev.abidux.morecontainers.menu.oven.OvenScreen;
import dev.abidux.morecontainers.menu.toaster.ToasterScreen;
import dev.abidux.morecontainers.menu.vault.VaultScreen;
import dev.abidux.morecontainers.network.Network;
import dev.abidux.morecontainers.renderer.block.IronChestBlockEntityRenderer;
import dev.abidux.morecontainers.renderer.block.SwordHolderBlockEntityRenderer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreContainers.MOD_ID)
public class MoreContainers {
    public static final String MOD_ID = "morecontainers";
    public MoreContainers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.registerPackets();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.TOASTER.get(), ToasterScreen::new);
            MenuScreens.register(ModMenuTypes.OVEN.get(), OvenScreen::new);
            MenuScreens.register(ModMenuTypes.FREEZER.get(), FreezerScreen::new);
            MenuScreens.register(ModMenuTypes.VAULT.get(), VaultScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.IRON_CHEST.get(), IronChestBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SWORD_HOLDER.get(), SwordHolderBlockEntityRenderer::new);
        }
    }
}