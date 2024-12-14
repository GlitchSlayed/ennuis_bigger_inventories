package io.github.ennuil.ennuis_bigger_inventories.impl.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public interface SplitTextureBeaconScreenButton {
	void ebi$setIconTexture(ResourceLocation textureId);
}
