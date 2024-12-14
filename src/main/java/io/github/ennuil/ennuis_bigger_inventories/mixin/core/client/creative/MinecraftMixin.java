package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	@Nullable
	public MultiPlayerGameMode gameMode;

	@Shadow
	@Final
	public Options options;

	@ModifyExpressionValue(method = "handleKeybinds", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original) {
		return this.gameMode.isTenfoursized() ? 10 : original;
	}

	// We get rid of the creative key checks too, so let's restore it
	@Inject(
		method = "handleKeybinds",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/Options;keySocialInteractions:Lnet/minecraft/client/KeyMapping;"
		)
	)
	private void checkFor10KeyCreativeHotbarPress(CallbackInfo ci) {
		if (this.options.keyHotbarSlots[9].consumeClick()) {
			CreativeModeInventoryScreen.handleHotbarLoadOrSave(
				(Minecraft) (Object) this,
				9,
				this.options.keyLoadHotbarActivator.consumeClick(),
				this.options.keySaveHotbarActivator.consumeClick()
			);
		}
	}

	@ModifyExpressionValue(method = "pickBlock", at = @At(value = "CONSTANT", args = "intValue=36"))
	private int modify36(int original) {
		return this.gameMode.isTenfoursized() ? 9 + 10 * 3 : original;
	}
}
