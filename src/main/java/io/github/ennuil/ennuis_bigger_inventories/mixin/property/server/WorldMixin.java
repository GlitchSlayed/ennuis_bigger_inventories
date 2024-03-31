package io.github.ennuil.ennuis_bigger_inventories.mixin.property.server;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.spongepowered.asm.mixin.Mixin;

@DedicatedServerOnly
@Mixin(World.class)
public abstract class WorldMixin implements EnnyWorld {
	@Override
	public boolean inferTenfoursized() {
		return ((ServerWorld) (Object) this).isTenfoursized();
	}
}
