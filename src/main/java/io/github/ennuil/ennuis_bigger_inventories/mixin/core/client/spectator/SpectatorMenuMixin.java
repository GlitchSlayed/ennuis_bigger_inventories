package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.spectator.SpectatorMenu;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(SpectatorMenu.class)
public abstract class SpectatorMenuMixin {
	@ModifyExpressionValue(method = "getCommand", at = @At(value = "CONSTANT", args = "intValue=6"))
	private int modify6(int original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? 7 : original;
	}

	@ModifyExpressionValue(method = "getCommand", at = @At(value = "CONSTANT", args = "intValue=7"))
	private int modify7(int original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? 8 : original;
	}

	@ModifyExpressionValue(method = {"getCommand", "getCommands"}, at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modify8s(int original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? 9 : original;
	}
}
