package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSummaryExtensions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelSummary.class)
public abstract class LevelSummaryMixin implements LevelSummaryExtensions {
	@Unique
	private boolean tenfoursized;

	public boolean ebi$isTenfoursized() {
		return this.tenfoursized;
	}

	public void ebi$setTenfoursized(boolean tenfoursized) {
		this.tenfoursized = tenfoursized;
	}

	@Inject(method = "createInfo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelSummary;hasCommands()Z"))
	private void append10x4Summary(CallbackInfoReturnable<Component> cir, @Local MutableComponent text) {
		if (this.ebi$isTenfoursized()) {
			text.append(", ").append(Component.translatable("selectWorld.ennuis_bigger_inventories.10x4"));
		} else {
			text.append(", ").append(Component.translatable("selectWorld.ennuis_bigger_inventories.9x4"));
		}
	}
}
