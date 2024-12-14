package io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface MultiPlayerGameModeExtensions {
	void ebi$setTenfoursized(boolean tenfoursized);
}
