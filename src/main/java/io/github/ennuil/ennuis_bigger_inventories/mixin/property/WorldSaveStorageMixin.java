package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSaveSummaryExtensions;
import net.minecraft.world.storage.WorldSaveStorage;
import net.minecraft.world.storage.WorldSaveSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldSaveStorage.class)
public abstract class WorldSaveStorageMixin {
	@ModifyReturnValue(method = "method_29015", at = @At(value = "RETURN", ordinal = 1))
	private WorldSaveSummary setTenfoursizedOnSummary(WorldSaveSummary original, @Local Dynamic<?> dynamic) {
		boolean tenfoursized = dynamic.get("ennuis_bigger_inventories:is_tenfoursized").asBoolean(false);
		((WorldSaveSummaryExtensions) original).ebi$setTenfoursized(tenfoursized);
		return original;
	}
}
