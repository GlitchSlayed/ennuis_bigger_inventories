package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements EnnyServerWorld {
	@Shadow
	@Final
	private ServerWorldProperties worldProperties;

	@Override
	public boolean isTenfoursized() {
		return this.worldProperties.isTenfoursized();
	}
}
