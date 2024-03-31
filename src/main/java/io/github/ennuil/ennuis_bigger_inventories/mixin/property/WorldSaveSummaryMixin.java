package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSaveSummaryExtensions;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.storage.WorldSaveSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldSaveSummary.class)
public abstract class WorldSaveSummaryMixin implements WorldSaveSummaryExtensions {
	@Unique
	private boolean tenfoursized;

	public boolean ebi$isTenfoursized() {
		return this.tenfoursized;
	}

	public void ebi$setTenfoursized(boolean tenfoursized) {
		this.tenfoursized = tenfoursized;
	}

	@Inject(method = "createDetails", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/storage/WorldSaveSummary;hasCheats()Z"))
	private void append10x4Summary(CallbackInfoReturnable<Text> cir, @Local MutableText text) {
		if (this.ebi$isTenfoursized()) {
			text.append(", ").append(Text.translatable("selectWorld.ennuis_bigger_inventories.10x4"));
		} else {
			text.append(", ").append(Text.translatable("selectWorld.ennuis_bigger_inventories.9x4"));
		}
	}
}
