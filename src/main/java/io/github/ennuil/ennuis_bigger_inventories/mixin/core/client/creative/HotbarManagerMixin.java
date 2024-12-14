package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.HotbarManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(HotbarManager.class)
public abstract class HotbarManagerMixin {
	@ModifyExpressionValue(method = {"<init>", "load", "save"}, at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNines(int original) {
		return 10;
	}
}
