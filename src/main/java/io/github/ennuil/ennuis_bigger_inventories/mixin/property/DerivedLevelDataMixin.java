package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerLevelData;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.ServerLevelDataExtensions;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DerivedLevelData.class)
public abstract class DerivedLevelDataMixin implements EnnyServerLevelData, ServerLevelDataExtensions {
	@Shadow
	@Final
	private ServerLevelData wrapped;

	@Override
	public boolean isTenfoursized() {
		return this.wrapped.isTenfoursized();
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {}
}
