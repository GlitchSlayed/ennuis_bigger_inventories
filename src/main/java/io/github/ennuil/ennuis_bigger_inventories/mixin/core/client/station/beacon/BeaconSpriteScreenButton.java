package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.station.beacon;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.SplitTextureBeaconScreenButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/screens/inventory/BeaconScreen$BeaconSpriteScreenButton")
public abstract class BeaconSpriteScreenButton implements SplitTextureBeaconScreenButton {
	@Unique
	private Identifier textureId;

	@Override
	public void ebi$setIconTexture(Identifier textureId) {
		this.textureId = textureId;
	}

	@ModifyArg(
		method = "extractIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"
		)
	)
	private Identifier modifyPatternTexture(Identifier original) {
		// I hate these so much; it's not even an EBI hackjob, it's a Mojang-like hackjob!
		if (Minecraft.getInstance().gameMode.isTenfoursized()) {
			return this.textureId;
		} else {
			return original;
		}
	}
}
