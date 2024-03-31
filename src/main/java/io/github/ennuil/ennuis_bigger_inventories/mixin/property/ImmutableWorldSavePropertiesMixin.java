package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerWorldProperties;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.ServerWorldPropertiesExtensions;
import net.minecraft.world.ImmutableWorldSaveProperties;
import net.minecraft.world.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ImmutableWorldSaveProperties.class)
public abstract class ImmutableWorldSavePropertiesMixin implements EnnyServerWorldProperties, ServerWorldPropertiesExtensions {
	@Shadow
	@Final
	private ServerWorldProperties worldProperties;

	@Override
	public boolean isTenfoursized() {
		return this.worldProperties.isTenfoursized();
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {}
}
