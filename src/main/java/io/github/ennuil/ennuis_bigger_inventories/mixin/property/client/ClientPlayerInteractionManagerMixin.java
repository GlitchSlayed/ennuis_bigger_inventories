package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyClientPlayerInteractionManager;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.ClientPlayerInteractionManagerExtensions;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@ClientOnly
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements EnnyClientPlayerInteractionManager, ClientPlayerInteractionManagerExtensions {
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
