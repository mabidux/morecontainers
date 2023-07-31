package dev.abidux.morecontainers.item;

import dev.abidux.morecontainers.MoreContainers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreContainers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MORE_CONTAINERS = CREATIVE_MODE_TABS.register("more_containers",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("More Containers"))
                    .icon(() -> ModItems.OVEN.get().getDefaultInstance())
                    .displayItems((s, output) -> {
                        Arrays.stream(ModItems.class.getDeclaredFields()).filter(f -> f.getType() == RegistryObject.class)
                                .map(f -> {
                                    try {
                                        return (RegistryObject<Item>) f.get(null);
                                    } catch (IllegalAccessException e) {return null;}
                                }).map(RegistryObject::get).forEach(output::accept);
                    })
                    .build());

}