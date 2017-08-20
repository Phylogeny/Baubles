package baubles.client.gui;

import org.lwjgl.opengl.GL11;

import baubles.common.network.PacketHandler;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.PacketOpenNormalInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiBaublesButton extends GuiButton {

	private final GuiContainer parentGui;

	public GuiBaublesButton(int buttonId, GuiContainer parentGui, int x, int y, int width, int height, String buttonText) {
		super(buttonId, x, parentGui.guiTop + y, width, height, buttonText);
		this.parentGui = parentGui;
	}
	
	
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		boolean pressed = super.mousePressed(mc, mouseX - this.parentGui.guiLeft, mouseY);
		if (pressed) {
			if (parentGui instanceof GuiInventory) {
				PacketHandler.INSTANCE.sendToServer(new PacketOpenBaublesInventory(mouseX, mouseY));
			} else {
				((GuiPlayerExpanded) parentGui).displayNormalInventory();
				PacketHandler.INSTANCE.sendToServer(new PacketOpenNormalInventory());
			}
		}
		return pressed;
	}



	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.visible)
		{
			int x = this.xPosition + this.parentGui.guiLeft;

			FontRenderer fontrenderer = mc.fontRendererObj;
			mc.getTextureManager().bindTexture(GuiPlayerExpanded.background);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= x && mouseY >= this.yPosition && mouseX < x + this.width && mouseY < this.yPosition + this.height;
			int k = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			if (k==1) {
				this.drawTexturedModalRect(x, this.yPosition, 200, 48, 10, 10);
			} else {
				this.drawTexturedModalRect(x, this.yPosition, 210, 48, 10, 10);
				this.drawCenteredString(fontrenderer, I18n.format(this.displayString), x + 5, this.yPosition + this.height, 0xffffff);
			}

			this.mouseDragged(mc, mouseX, mouseY);
		}
	}
}
