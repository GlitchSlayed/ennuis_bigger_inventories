package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecartChest.class)
public abstract class MinecartChestMixin extends AbstractMinecartContainer {
	private MinecartChestMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@ModifyReturnValue(method = "getContainerSize", at = @At("RETURN"))
	private int modifySize(int original) {
		return this.level().inferTenfoursized() ? 10 * 3 : original;
	}

	@WrapMethod(method = "createMenu")
	private AbstractContainerMenu returnTenfoursizedMenu(int syncId, Inventory inventory, Operation<AbstractContainerMenu> original) {
		if (inventory.isTenfoursized()) {
			return TenfoursizedContainerMenu.threeRows(syncId, inventory, (MinecartChest) (Object) this);
		} else {
			return original.call(syncId, inventory);
		}
	}
}
