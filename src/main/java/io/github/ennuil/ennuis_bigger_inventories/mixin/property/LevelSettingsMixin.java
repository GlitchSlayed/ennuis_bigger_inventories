package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSettingsExtensions;
import net.minecraft.world.level.LevelSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelSettings.class)
public abstract class LevelSettingsMixin implements LevelSettingsExtensions {
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

	@ModifyReturnValue(method = "parse", at = @At("RETURN"))
	private static LevelSettings readProperty(LevelSettings original, Dynamic<?> dynamic) {
		boolean value = dynamic.get("ennuis_bigger_inventories:is_tenfoursized").asBoolean(false);
		((LevelSettingsExtensions) (Object) original).ebi$setTenfoursized(value);
		HackjobKitImpl.TenfoursizedProperty.setInstance(value);
		return original;
	}

	@ModifyReturnValue(
		method = {
			"withGameType",
			"withDifficulty",
			"withDataConfiguration",
			"copy"
		},
		at = @At("RETURN")
	)
	private LevelSettings copyTenfoursized(LevelSettings original) {
		((LevelSettingsExtensions) (Object) original).ebi$setTenfoursized(this.tenfoursized);
		return original;
	}
}
