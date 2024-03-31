package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChestMinecartEntity.class)
public abstract class ChestMinecartEntityMixin extends StorageMinecartEntity {
	private ChestMinecartEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@ModifyReturnValue(method = "size", at = @At("RETURN"))
	private int modifySize(int original) {
		return this.getWorld().inferTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "getScreenHandler", at = @At(value = "RETURN"))
	private ScreenHandler return10x3ScreenHandler(ScreenHandler original, int syncId, PlayerInventory playerInventory) {
		if (playerInventory.isTenfoursized()) {
			return GenericTensizedContainerScreenHandler.createGeneric10x3(syncId, playerInventory, (ChestMinecartEntity) (Object) this);
		} else {
			return original;
		}
	}
}
