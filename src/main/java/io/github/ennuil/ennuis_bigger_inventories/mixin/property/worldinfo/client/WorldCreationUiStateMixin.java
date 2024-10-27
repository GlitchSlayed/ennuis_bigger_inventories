package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldCreationUiStateExtensions;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@ClientOnly
@Mixin(WorldCreationUiState.class)
public abstract class WorldCreationUiStateMixin implements WorldCreationUiStateExtensions {
	@Shadow
	public abstract void onChanged();

	@Unique
	private boolean tenfoursized = true;

	@Override
	public boolean ebi$isTenfoursized() {
		return this.tenfoursized;
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {
		this.tenfoursized = tenfoursized;
		this.onChanged();
	}
}
