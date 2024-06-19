package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BeaconScreen.class)
public interface BeaconScreenAccessor {
	@Accessor("BUTTON_DISABLED")
	static Identifier getButtonDisabled() {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("BUTTON_SELECTED")
	static Identifier getButtonSelected() {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("BUTTON_HIGHLIGHTED")
	static Identifier getButtonHighlighted() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
