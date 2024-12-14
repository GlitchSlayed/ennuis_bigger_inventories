package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyMultiPlayerGameMode;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.MultiPlayerGameModeExtensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin implements EnnyMultiPlayerGameMode, MultiPlayerGameModeExtensions {
	@Unique
	private boolean tenfoursized = false;

	@Override
	public boolean isTenfoursized() {
		return this.tenfoursized;
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {
		this.tenfoursized = tenfoursized;
	}
}
