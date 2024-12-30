package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import io.github.ennuil.ennuis_bigger_inventories.api.EBILevel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(Level.class)
public abstract class LevelMixin implements EBILevel {
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
