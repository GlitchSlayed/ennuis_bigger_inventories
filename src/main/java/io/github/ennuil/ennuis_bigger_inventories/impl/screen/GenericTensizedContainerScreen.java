package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class GenericTensizedContainerScreen extends HandledScreen<GenericTensizedContainerScreenHandler> implements ScreenHandlerProvider<GenericTensizedContainerScreenHandler> {
	private static final Identifier TEXTURE = new Identifier("ennuis_bigger_inventories", "textures/gui/container/generic_10x6.png");
	private final int rows;

	public GenericTensizedContainerScreen(GenericTensizedContainerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.rows = handler.getRows();
		this.backgroundHeight = 114 + this.rows * 18;
		this.playerInventoryTitleY = this.backgroundHeight - 94;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(graphics);
		super.render(graphics, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;
		graphics.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
		graphics.drawTexture(TEXTURE, x, y + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
	}
}
