package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldCreatorExtensions;
import net.minecraft.client.world.WorldCreator;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@ClientOnly
@Mixin(WorldCreator.class)
public abstract class WorldCreatorMixin implements WorldCreatorExtensions {
	@Shadow
	public abstract void generate();

	@Unique
	private boolean tenfoursized = true;

	@Override
	public boolean ebi$isTenfoursized() {
		return this.tenfoursized;
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {
		this.tenfoursized = tenfoursized;
		this.generate();
	}
}
