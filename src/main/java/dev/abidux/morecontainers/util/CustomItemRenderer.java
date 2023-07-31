package dev.abidux.morecontainers.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class CustomItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final Supplier<BlockEntity> blockEntitySupplier;
    public CustomItemRenderer(Supplier<BlockEntity> blockEntitySupplier) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public void renderByItem(ItemStack item, ItemDisplayContext context, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntitySupplier.get(), stack, buffer, light, overlay);
    }
}