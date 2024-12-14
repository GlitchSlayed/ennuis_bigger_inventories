package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(SpectatorMenu.class)
public abstract class SpectatorMenuMixin {
	@ModifyExpressionValue(method = "getItem", at = @At(value = "CONSTANT", args = "intValue=6"))
	private int modify6(int original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? 7 : original;
	}

	@ModifyExpressionValue(method = "getItem", at = @At(value = "CONSTANT", args = "intValue=7"))
	private int modify7(int original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? 8 : original;
	}

	@ModifyExpressionValue(method = {"getItem", "getItems"}, at = @At(value = "CONSTANT", args = "intValue=8"))
	private int modify8s(int original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? 9 : original;
	}
}
