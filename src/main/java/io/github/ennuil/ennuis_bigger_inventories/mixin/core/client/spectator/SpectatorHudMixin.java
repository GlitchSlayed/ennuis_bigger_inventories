package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuState;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(SpectatorHud.class)
public abstract class SpectatorHudMixin {
	@Unique private static final Identifier EBI_HOTBAR_TEXTURE = new Identifier("ennuis_bigger_inventories", "hud/hotbar");
	@Unique private static final Identifier EBI_HOTBAR_SELECTION_TEXTURE = new Identifier("ennuis_bigger_inventories", "hud/hotbar_selection");

	@Shadow
	@Final
	private MinecraftClient client;

	@WrapOperation(
		method = "renderSpectatorMenu",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 0
		)
	)
	private void modifyHotbar(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) int ox) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_HOTBAR_TEXTURE, ox - 101, y, 202, height);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@WrapOperation(
		method = "renderSpectatorMenu",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	private void modifyHotbarSelection(GuiGraphics instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) int ox, @Local(argsOnly = true) SpectatorMenuState state) {
		if (this.client.interactionManager.isTenfoursized()) {
			instance.drawGuiTexture(EBI_HOTBAR_SELECTION_TEXTURE, ox - 102 + state.getSelectedSlot() * 20, y, width, 23);
		} else {
			original.call(instance, texture, x, y, u, v, width, height);
		}
	}

	@ModifyExpressionValue(method = "renderSpectatorMenu", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original) {
		return this.client.interactionManager.isTenfoursized() ? 10 : original;
	}

	@ModifyExpressionValue(method = "renderSpectatorMenu", at = @At(value = "CONSTANT", args = "intValue=90"))
	private int modify90(int original) {
		return this.client.interactionManager.isTenfoursized() ? 100 : original;
	}

	@ModifyExpressionValue(method = "cycleSlot", at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modify8s(int original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? 9 : original;
	}
}
