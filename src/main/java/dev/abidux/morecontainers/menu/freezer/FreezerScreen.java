package dev.abidux.morecontainers.menu.freezer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FreezerScreen extends AbstractContainerScreen<FreezerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
    public FreezerScreen(FreezerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float p_281886_) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, p_281886_);
        renderTooltip(graphics, mouseX, mouseY);
    }
}