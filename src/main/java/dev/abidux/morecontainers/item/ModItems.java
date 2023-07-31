package dev.abidux.morecontainers.item;

import dev.abidux.morecontainers.MoreContainers;
import dev.abidux.morecontainers.util.BlockEntityItem;
import dev.abidux.morecontainers.block.ModBlocks;
import dev.abidux.morecontainers.block.custom.entity.IronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreContainers.MOD_ID);

    public static final RegistryObject<Item> IRON_CHEST = ITEMS.register("iron_chest", () -> new BlockEntityItem(ModBlocks.IRON_CHEST.get(),
            () -> new IronChestBlockEntity(BlockPos.ZERO, ModBlocks.IRON_CHEST.get().defaultBlockState()),
            new Item.Properties()));

    public static final RegistryObject<Item> TOASTER = ITEMS.register("toaster", () -> new BlockItem(ModBlocks.TOASTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> OVEN = ITEMS.register("oven", () -> new BlockItem(ModBlocks.OVEN.get(), new Item.Properties()));
    public static final RegistryObject<Item> SWORD_HOLDER = ITEMS.register("sword_holder", () -> new BlockItem(ModBlocks.SWORD_HOLDER.get(), new Item.Properties()));
    public static final RegistryObject<Item> FREEZER = ITEMS.register("freezer", () -> new BlockItem(ModBlocks.FREEZER.get(), new Item.Properties()));
    public static final RegistryObject<Item> VAULT = ITEMS.register("vault", () -> new BlockItem(ModBlocks.VAULT.get(), new Item.Properties()));

}