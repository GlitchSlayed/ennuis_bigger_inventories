package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
	@Shadow
	protected int backgroundWidth;

	private HandledScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void modifyBackgroundWidth(ScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
		if (inventory.isTenfoursized()) {
			this.backgroundWidth = 194;
		}
	}

	@ModifyExpressionValue(method = {"handleHotbarKeyPressed", "onMouseClick(I)V"}, at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = {"handleHotbarKeyPressed", "onMouseClick(I)V"}, at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original) {
		return this.client.interactionManager.isTenfoursized() ? 40 + 4 : original;
	}
}
