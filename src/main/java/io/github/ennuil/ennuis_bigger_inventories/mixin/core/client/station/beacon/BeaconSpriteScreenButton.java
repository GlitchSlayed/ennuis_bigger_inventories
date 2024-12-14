package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureBeaconScreenButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/screens/inventory/BeaconScreen$BeaconSpriteScreenButton")
public abstract class BeaconSpriteScreenButton implements SplitTextureBeaconScreenButton {
	@Unique
	private ResourceLocation textureId;

	@Override
	public void ebi$setIconTexture(ResourceLocation textureId) {
		this.textureId = textureId;
	}

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		)
	)
	private ResourceLocation modifyPatternTexture(ResourceLocation original) {
		// I hate these so much; it's not even an EBI hackjob, it's a Mojang-like hackjob!
		if (Minecraft.getInstance().gameMode.isTenfoursized()) {
			return this.textureId;
		} else {
			return original;
		}
	}
}
