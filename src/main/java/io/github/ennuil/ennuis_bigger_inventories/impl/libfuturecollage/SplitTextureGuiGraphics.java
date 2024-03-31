package io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@ClientOnly
@InjectedInterface(GuiGraphics.class)
public interface SplitTextureGuiGraphics {
	default void drawGuiTexture(Identifier texture, int x, int y, int width, int height) {
		throw new IllegalStateException("Mixin injection failed");
	}

	default void drawGuiTexture(Identifier texture, int x, int y, int z, int width, int height) {
		throw new IllegalStateException("Mixin injection failed");
	}

	default void drawGuiTexture(Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height) {
		throw new IllegalStateException("Mixin injection failed");
	}

	default void drawGuiTexture(Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int z, int width, int height) {
		throw new IllegalStateException("Mixin injection failed");
	}
}
