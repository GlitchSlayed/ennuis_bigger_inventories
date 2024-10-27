package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin {
	@Shadow
	protected Minecraft minecraft;

	@ModifyExpressionValue(method = "updateScreenPosition", at = @At(value = "CONSTANT", args = "intValue=177"))
	private int modifyNineSevenConstant(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 186 : original;
	}
}
