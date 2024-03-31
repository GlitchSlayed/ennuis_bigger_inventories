package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChestBoatEntity.class)
public abstract class ChestBoatEntityMixin extends BoatEntity {
	private ChestBoatEntityMixin(EntityType<? extends BoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyArg(
		method = {
			"<init>(Lnet/minecraft/world/World;DDD)V",
			"<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/collection/DefaultedList;ofSize(ILjava/lang/Object;)Lnet/minecraft/util/collection/DefaultedList;"
		)
	)
	private int modifySizeOnInit(int original, @Local(argsOnly = true) World world) {
		return world.inferTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "size", at = @At("RETURN"))
	private int modifySize(int original) {
		return this.getWorld().inferTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "createMenu", at = @At(value = "RETURN", ordinal = 0))
	private ScreenHandler return10x3ScreenHandler(ScreenHandler original, int syncId, PlayerInventory playerInventory) {
		if (this.getWorld().inferTenfoursized()) {
			return GenericTensizedContainerScreenHandler.createGeneric10x3(syncId, playerInventory, (ChestBoatEntity) (Object) this);
		} else {
			return original;
		}
    }
}
