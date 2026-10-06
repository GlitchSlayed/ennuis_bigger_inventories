package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.contextualbar;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ContextualBar.class)
public interface ContextualBarMixin {
	// ContextualBar.left() computes (guiWidth - 182) / 2 to center the bar.
	// This must match the actual bar width so centering aligns with the wider hotbar.
	@ModifyExpressionValue(method = "left", at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modifyBarWidth(int original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? 202 : original;
	}
}
