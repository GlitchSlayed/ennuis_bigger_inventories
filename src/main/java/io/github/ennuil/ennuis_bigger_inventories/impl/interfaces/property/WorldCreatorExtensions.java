package io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property;

import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface WorldCreatorExtensions {
	boolean ebi$isTenfoursized();
	void ebi$setTenfoursized(boolean tenfoursized);
}
