package dev.abidux.morecontainers.menu;

import dev.abidux.morecontainers.MoreContainers;
import dev.abidux.morecontainers.menu.freezer.FreezerMenu;
import dev.abidux.morecontainers.menu.oven.OvenMenu;
import dev.abidux.morecontainers.menu.toaster.ToasterMenu;
import dev.abidux.morecontainers.menu.vault.VaultMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MoreContainers.MOD_ID);

    public static final RegistryObject<MenuType<ToasterMenu>> TOASTER = MENUS.register("toaster",
            () -> IForgeMenuType.create(ToasterMenu::new));

    public static final RegistryObject<MenuType<OvenMenu>> OVEN = MENUS.register("oven",
            () -> IForgeMenuType.create(OvenMenu::new));

    public static final RegistryObject<MenuType<FreezerMenu>> FREEZER = MENUS.register("freezer",
            () -> IForgeMenuType.create(FreezerMenu::new));

    public static final RegistryObject<MenuType<VaultMenu>> VAULT = MENUS.register("vault",
            () -> IForgeMenuType.create(VaultMenu::new));

}