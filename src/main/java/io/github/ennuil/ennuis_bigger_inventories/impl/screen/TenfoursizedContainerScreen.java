package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class TenfoursizedContainerScreen extends AbstractContainerScreen<TenfoursizedContainerMenu> implements MenuAccess<TenfoursizedContainerMenu> {
	private static final Identifier TEXTURE = ModUtils.id("textures/gui/container/generic_10x6.png");
	private final int rows;

	public TenfoursizedContainerScreen(TenfoursizedContainerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 194, 114 + menu.getRowCount() * 18);
		this.rows = menu.getRowCount();
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, this.imageWidth, this.rows * 18 + 17, 256, 256);
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y + this.rows * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
		super.extractContents(graphics, mouseX, mouseY, delta);
	}
}
