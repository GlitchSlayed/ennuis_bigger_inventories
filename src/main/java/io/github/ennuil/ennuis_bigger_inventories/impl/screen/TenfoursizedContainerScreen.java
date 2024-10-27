package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class TenfoursizedContainerScreen extends AbstractContainerScreen<TenfoursizedContainerMenu> implements MenuAccess<TenfoursizedContainerMenu> {
	private static final ResourceLocation TEXTURE = ModUtils.id("textures/gui/container/generic_10x6.png");
	private final int rows;

	public TenfoursizedContainerScreen(TenfoursizedContainerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.rows = menu.getRowCount();
		this.imageHeight = 114 + this.rows * 18;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(graphics, mouseX, mouseY, delta);
		super.render(graphics, mouseX, mouseY, delta);
		this.renderTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		graphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.rows * 18 + 17);
		graphics.blit(TEXTURE, x, y + this.rows * 18 + 17, 0, 126, this.imageWidth, 96);
	}
}
