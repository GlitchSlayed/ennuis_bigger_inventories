package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ClientOnly
@Mixin(World.class)
public abstract class WorldMixin implements EnnyWorld {
	@Shadow
	public abstract boolean isClient();

	@Override
	public boolean inferTenfoursized() {
		if (!this.isClient()) {
			return ((ServerWorld) (Object) this).isTenfoursized();
		} else {
			return ((ClientWorldAccessor) this).getClient().interactionManager.isTenfoursized();
		}
	}
}
