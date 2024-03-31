package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import net.minecraft.world.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldInfo.class)
public abstract class WorldInfoMixin implements WorldInfoExtensions {
	@Unique
	private boolean tenfoursized = false;

	@Override
	public boolean ebi$isTenfoursized() {
		return this.tenfoursized;
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {
		this.tenfoursized = tenfoursized;
	}

	@ModifyReturnValue(method = "fromDynamic", at = @At("RETURN"))
	private static WorldInfo readProperty(WorldInfo original, Dynamic<?> dynamic) {
		boolean value = dynamic.get("ennuis_bigger_inventories:is_tenfoursized").asBoolean(false);
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(value);
		HackjobKitImpl.TenfoursizedProperty.setInstance(value);
		return original;
	}

	@ModifyReturnValue(
		method = {
			"withGameMode",
			"withDifficulty",
			"withFeatureAndDataSettings",
			"withCopiedGameRules"
		},
		at = @At("RETURN")
	)
	private WorldInfo copyTenfoursized(WorldInfo original) {
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(this.tenfoursized);
		return original;
	}
}
