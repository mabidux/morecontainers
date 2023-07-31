package dev.abidux.morecontainers.block;

import dev.abidux.morecontainers.MoreContainers;
import dev.abidux.morecontainers.block.custom.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreContainers.MOD_ID);

    public static final RegistryObject<Block> IRON_CHEST = BLOCKS.register("iron_chest", IronChestBlock::new);
    public static final RegistryObject<Block> TOASTER = BLOCKS.register("toaster", ToasterBlock::new);
    public static final RegistryObject<Block> OVEN = BLOCKS.register("oven", OvenBlock::new);
    public static final RegistryObject<Block> SWORD_HOLDER = BLOCKS.register("sword_holder", SwordHolderBlock::new);
    public static final RegistryObject<Block> FREEZER = BLOCKS.register("freezer", FreezerBlock::new);
    public static final RegistryObject<Block> VAULT = BLOCKS.register("vault", VaultBlock::new);

}