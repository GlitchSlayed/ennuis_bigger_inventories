package io.github.ennuil.ennuis_bigger_inventories.mixin.property.server;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.spongepowered.asm.mixin.Mixin;

@DedicatedServerOnly
@Mixin(Level.class)
public abstract class LevelMixin implements EnnyLevel {
	@Override
	public boolean inferTenfoursized() {
		return ((ServerLevel) (Object) this).isTenfoursized();
	}
}
