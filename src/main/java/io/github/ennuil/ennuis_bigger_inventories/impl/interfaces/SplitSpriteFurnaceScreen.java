package io.github.ennuil.ennuis_bigger_inventories.impl.interfaces;

import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface SplitSpriteFurnaceScreen {
	void ebi$setProgressSprites(ResourceLocation burnProgressSprite, ResourceLocation litProgressSprite);
}
