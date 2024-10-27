package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractMinecartContainer.class)
public abstract class AbstractMinecartContainerMixin {
	@ModifyArg(
		method = {
			"<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V",
			"<init>(Lnet/minecraft/world/entity/EntityType;DDDLnet/minecraft/world/level/Level;)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"
		)
	)
	private int modifySize(int original, @Local(argsOnly = true) Level level) {
		return level.inferTenfoursized() ? 10 * 4 : original;
	}
}
