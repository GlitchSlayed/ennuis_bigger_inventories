package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StorageMinecartEntity.class)
public abstract class StorageMinecartEntityMixin {
	@ModifyArg(
		method = {
			"<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
			"<init>(Lnet/minecraft/entity/EntityType;DDDLnet/minecraft/world/World;)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/collection/DefaultedList;ofSize(ILjava/lang/Object;)Lnet/minecraft/util/collection/DefaultedList;"
		)
	)
	private int modifySize(int original, @Local(argsOnly = true) World world) {
		return world.inferTenfoursized() ? 10 * 4 : original;
	}
}
