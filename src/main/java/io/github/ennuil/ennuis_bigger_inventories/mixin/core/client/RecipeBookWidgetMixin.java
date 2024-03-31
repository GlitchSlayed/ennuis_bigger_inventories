package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipe.book.RecipeBookWidget;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(RecipeBookWidget.class)
public abstract class RecipeBookWidgetMixin {
	@Shadow
	protected MinecraftClient client;

	@ModifyExpressionValue(method = "findLeftEdge", at = @At(value = "CONSTANT", args = "intValue=177"))
	private int modifyNineSevenConstant(int original) {
		return this.client.interactionManager.isTenfoursized() ? 186 : original;
	}
}
