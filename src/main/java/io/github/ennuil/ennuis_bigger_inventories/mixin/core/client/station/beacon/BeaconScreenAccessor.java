package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(BeaconScreen.class)
public interface BeaconScreenAccessor {
	@Accessor("BUTTON_DISABLED_SPRITE")
	static ResourceLocation getButtonDisabledSprite() {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("BUTTON_SELECTED_SPRITE")
	static ResourceLocation getButtonSelectedSprite() {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("BUTTON_HIGHLIGHTED_SPRITE")
	static ResourceLocation getButtonHighlightedSprite() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
