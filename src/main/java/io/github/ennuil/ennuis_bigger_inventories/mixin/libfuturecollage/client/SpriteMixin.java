package io.github.ennuil.ennuis_bigger_inventories.mixin.libfuturecollage.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage.SplitTextureSprite;
import net.minecraft.client.texture.Sprite;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ClientOnly
@Mixin(Sprite.class)
public abstract class SpriteMixin implements SplitTextureSprite {
	@Shadow
	@Final
	private float uMax;

	@Shadow
	@Final
	private float uMin;

	@Shadow
	@Final
	private float vMin;

	@Shadow
	@Final
	private float vMax;

	@Override
	public float lfc$getFrameU(float frame) {
		return this.uMin + (this.uMax - this.uMin) * frame;
	}

	@Override
	public float lfc$getFrameV(float frame) {
		return this.vMin + (this.vMax - this.vMin) * frame;
	}
}
