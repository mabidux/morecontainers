package dev.abidux.morecontainers.block;

import dev.abidux.morecontainers.MoreContainers;
import dev.abidux.morecontainers.block.custom.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreContainers.MOD_ID);

    public static final RegistryObject<BlockEntityType<IronChestBlockEntity>> IRON_CHEST = BLOCK_ENTITIES.register("iron_chest",
            () -> BlockEntityType.Builder.of(IronChestBlockEntity::new, ModBlocks.IRON_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<ToasterBlockEntity>> TOASTER = BLOCK_ENTITIES.register("toaster",
            () -> BlockEntityType.Builder.of(ToasterBlockEntity::new, ModBlocks.TOASTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN = BLOCK_ENTITIES.register("oven",
            () -> BlockEntityType.Builder.of(OvenBlockEntity::new, ModBlocks.OVEN.get()).build(null));

    public static final RegistryObject<BlockEntityType<SwordHolderBlockEntity>> SWORD_HOLDER = BLOCK_ENTITIES.register("sword_holder",
            () -> BlockEntityType.Builder.of(SwordHolderBlockEntity::new, ModBlocks.SWORD_HOLDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FreezerBlockEntity>> FREEZER = BLOCK_ENTITIES.register("freezer",
            () -> BlockEntityType.Builder.of(FreezerBlockEntity::new, ModBlocks.FREEZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<VaultBlockEntity>> VAULT = BLOCK_ENTITIES.register("vault",
            () -> BlockEntityType.Builder.of(VaultBlockEntity::new, ModBlocks.VAULT.get()).build(null));

}