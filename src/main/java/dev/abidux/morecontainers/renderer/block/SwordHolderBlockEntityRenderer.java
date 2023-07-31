package dev.abidux.morecontainers.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.abidux.morecontainers.block.ModBlocks;
import dev.abidux.morecontainers.block.custom.SwordHolderBlock;
import dev.abidux.morecontainers.block.custom.entity.SwordHolderBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.joml.Quaternionf;

public class SwordHolderBlockEntityRenderer implements BlockEntityRenderer<SwordHolderBlockEntity> {

    private final ItemRenderer itemRenderer;
    public SwordHolderBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SwordHolderBlockEntity entity, float partialTick, PoseStack stack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (entity.getLevel() == null) return;
        BlockState state = entity.getLevel().getBlockState(entity.getBlockPos());
        if (!state.is(ModBlocks.SWORD_HOLDER.get())) return;
        Direction direction = state.getValue(SwordHolderBlock.FACING);

        entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            stack.pushPose();
            stack.scale(.9f,.9f, .9f);
            stack.translate(.55, .6, .55);
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                stack.mulPose(new Quaternionf(0, 0, 0, 1).rotateX((float)Math.toRadians(180)).rotateZ((float)Math.toRadians(45)));
            } else {
                stack.mulPose(new Quaternionf(0, 0, 0, 1).rotateX((float)Math.toRadians(90)).rotateY((float)Math.toRadians(90)).rotateZ((float)Math.toRadians(135)));
            }
            itemRenderer.renderStatic(handler.getStackInSlot(0), ItemDisplayContext.GUI, packedLight, packedOverlay, stack, buffer, entity.getLevel(), 1);
            stack.popPose();
        });
    }
}