package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin {
	@Shadow
	protected Minecraft minecraft;

	@ModifyExpressionValue(method = "updateScreenPosition", at = @At(value = "CONSTANT", args = "intValue=177"))
	private int modifyNineSevenConstant(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 186 : original;
	}
}
