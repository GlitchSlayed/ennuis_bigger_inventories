package io.github.ennuil.ennuis_bigger_inventories.impl.interfaces;

import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface SplitTextureFurnaceScreen {
	void ebi$setProgressTextures(Identifier burnProgressTexture, Identifier litProgressTexture);
}
