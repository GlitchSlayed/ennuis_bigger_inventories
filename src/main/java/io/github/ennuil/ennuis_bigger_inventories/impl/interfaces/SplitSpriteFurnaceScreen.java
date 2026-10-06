package io.github.ennuil.ennuis_bigger_inventories.impl.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public interface SplitSpriteFurnaceScreen {
	void ebi$setProgressSprites(Identifier burnProgressSprite, Identifier litProgressSprite);
}
