package dev.abidux.morecontainers.renderer.block;

import dev.abidux.morecontainers.MoreContainers;
import dev.abidux.morecontainers.block.custom.entity.IronChestBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;

public class IronChestBlockEntityRenderer extends ChestRenderer<IronChestBlockEntity> {
    private static final Material MATERIAL = chestMaterial("iron_chest");

    public IronChestBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    protected Material getMaterial(IronChestBlockEntity blockEntity, ChestType chestType) {
        return MATERIAL;
    }

    public static Material chestMaterial(String chestName) {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(MoreContainers.MOD_ID, "entity/chest/" + chestName));
    }
}