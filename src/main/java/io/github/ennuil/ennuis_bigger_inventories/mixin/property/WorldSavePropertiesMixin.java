package io.github.ennuil.ennuis_bigger_inventories.mixin.property;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import io.github.ennuil.ennuis_bigger_inventories.api.EnnyServerWorldProperties;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.ServerWorldPropertiesExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSavePropertiesExtensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.WorldInfo;
import net.minecraft.world.WorldSaveProperties;
import net.minecraft.world.gen.WorldGeneratorOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSaveProperties.class)
public abstract class WorldSavePropertiesMixin implements EnnyServerWorldProperties, ServerWorldPropertiesExtensions, WorldSavePropertiesExtensions {
	@Shadow
	private WorldInfo levelInfo;

	@Shadow
	protected abstract void updateProperties(DynamicRegistryManager registryManager, NbtCompound levelNbt, @Nullable NbtCompound playerNbt);

	@Override
	public boolean isTenfoursized() {
		return ((WorldInfoExtensions) (Object) this.levelInfo).ebi$isTenfoursized();
	}

	@Override
	public void ebi$setTenfoursized(boolean tenfoursized) {
		((WorldInfoExtensions) (Object) this.levelInfo).ebi$setTenfoursized(tenfoursized);
	}

	@ModifyReturnValue(method = "readProperties", at = @At("RETURN"))
	private static <T> WorldSaveProperties readTensizedProperty(WorldSaveProperties original, Dynamic<T> dynamic, WorldInfo worldInfo, WorldSaveProperties.WorldType worldType, WorldGeneratorOptions worldGenOptions, Lifecycle lifecycle) {
		boolean value = ((WorldInfoExtensions) (Object) worldInfo).ebi$isTenfoursized();
		((ServerWorldPropertiesExtensions) original).ebi$setTenfoursized(value);
		return original;
	}

	@Inject(method = "updateProperties", at = @At("TAIL"))
	private void updateTensizedProperty(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {
		levelNbt.putBoolean("ennuis_bigger_inventories:is_tenfoursized", ((WorldInfoExtensions) (Object) this.levelInfo).ebi$isTenfoursized());
	}

	@Override
	public NbtCompound ebi$cloneWorldNbtAndTenfoursize(DynamicRegistryManager registryManager) {
		var nbt = new NbtCompound();
		this.updateProperties(registryManager, nbt, null);
		nbt.putBoolean("ennuis_bigger_inventories:is_tenfoursized", true);
		return nbt;
	}
}
