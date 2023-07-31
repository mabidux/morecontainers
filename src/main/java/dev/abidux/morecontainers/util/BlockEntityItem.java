package dev.abidux.morecontainers.util;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockEntityItem extends BlockItem {

    private final Supplier<BlockEntity> entitySupplier;
    public BlockEntityItem(Block pBlock, Supplier<BlockEntity> entitySupplier, Properties pProperties) {
        super(pBlock, pProperties);
        this.entitySupplier = entitySupplier;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new CustomItemRenderer(entitySupplier);
            }
        });
    }
}