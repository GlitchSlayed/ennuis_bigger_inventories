package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.contextualbar;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.contextualbar.LocatorBar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LocatorBar.class)
public class LocatorBarMixin {
	@Shadow @Final private Minecraft minecraft;

	// The background sprite width: blitSprite(..., 182, 5)
	@ModifyExpressionValue(method = "extractBackground", at = @At(value = "CONSTANT", args = "intValue=182"))
	private int modifyBarWidth(int original) {
		return this.minecraft.gameMode.isTenfoursized() ? 202 : original;
	}
}
