package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin extends Screen {
	@Shadow
	protected int imageWidth;

	private AbstractContainerScreenMixin(Component title) {
		super(title);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void modifyBackgroundWidth(AbstractContainerMenu menu, Inventory inventory, Component title, CallbackInfo ci) {
		if (inventory.isTenfoursized()) {
			this.imageWidth = 194;
		}
	}

	@ModifyExpressionValue(method = {"checkHotbarMouseClicked", "checkHotbarKeyPressed"}, at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = {"checkHotbarMouseClicked", "checkHotbarKeyPressed"}, at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 40 + 4 : original;
	}
}
