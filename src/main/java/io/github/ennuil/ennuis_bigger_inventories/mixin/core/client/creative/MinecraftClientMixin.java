package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow
	@Nullable
	public ClientPlayerInteractionManager interactionManager;

	@Shadow
	@Final
	public GameOptions options;

	@ModifyExpressionValue(method = "handleInputEvents", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original) {
		return this.interactionManager.isTenfoursized() ? 10 : original;
	}

	// We get rid of the creative key checks too, so let's restore it
	@Inject(
		method = "handleInputEvents",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/option/GameOptions;socialInteractionsKey:Lnet/minecraft/client/option/KeyBind;"
		)
	)
	private void checkFor10KeyCreativeHotbarPress(CallbackInfo ci) {
		if (this.options.hotbarKeys[9].wasPressed()) {
			CreativeInventoryScreen.onHotbarKeyPress(
				(MinecraftClient) (Object) this,
				9,
				this.options.loadToolbarActivatorKey.wasPressed(),
				this.options.saveToolbarActivatorKey.wasPressed()
			);
		}
	}

	@ModifyExpressionValue(method = "doItemPick", at = @At(value = "CONSTANT", args = "intValue=36"))
	private int modify36(int original) {
		return this.interactionManager.isTenfoursized() ? 9 + 10 * 3 : original;
	}
}
