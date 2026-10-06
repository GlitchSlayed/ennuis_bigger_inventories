package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin extends Screen {
	private AbstractContainerScreenMixin(Component title) {
		super(title);
	}

	@ModifyExpressionValue(
		method = "<init>(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;)V",
		at = @At(value = "CONSTANT", args = "intValue=176")
	)
	private static int modifyBackgroundWidth(int original, @Local(argsOnly = true) Inventory inventory) {
		return inventory.isTenfoursized() ? 194 : original;
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
