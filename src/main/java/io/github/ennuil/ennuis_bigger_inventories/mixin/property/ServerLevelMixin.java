package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements EnnyServerLevel {
	@Shadow
	@Final
	private ServerLevelData serverLevelData;

	@Override
	public boolean isTenfoursized() {
		return this.serverLevelData.isTenfoursized();
	}
}
