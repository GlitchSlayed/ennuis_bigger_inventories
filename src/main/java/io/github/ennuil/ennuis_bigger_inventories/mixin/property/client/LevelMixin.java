package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ClientOnly
@Mixin(Level.class)
public abstract class LevelMixin implements EnnyLevel {
	@Shadow
	public abstract boolean isClientSide();

	@Override
	public boolean inferTenfoursized() {
		if (!this.isClientSide()) {
			return ((ServerLevel) (Object) this).isTenfoursized();
		} else {
			return ((ClientLevelAccessor) this).getMinecraft().gameMode.isTenfoursized();
		}
	}
}
