package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSummaryExtensions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelStorageSource.class)
public abstract class LevelStorageSourceMixin {
	@ModifyReturnValue(method = "makeLevelSummary", at = @At(value = "RETURN"))
	private LevelSummary setTenfoursizedOnSummary(LevelSummary original, @Local(argsOnly = true) Dynamic<?> dynamic) {
		boolean tenfoursized = dynamic.get("ennuis_bigger_inventories:is_tenfoursized").asBoolean(false);
		((LevelSummaryExtensions) original).ebi$setTenfoursized(tenfoursized);
		return original;
	}
}
