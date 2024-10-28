package io.github.ennuil.ennuis_bigger_inventories.mixin.core.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.AbstractChestBoat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Supplier;

@Mixin(AbstractChestBoat.class)
public abstract class AbstractChestBoatMixin extends AbstractBoat {
	private AbstractChestBoatMixin(EntityType<? extends AbstractBoat> entityType, Level level, Supplier<Item> supplier) {
		super(entityType, level, supplier);
	}

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"
		)
	)
	private int modifySizeOnInit(int original, @Local(argsOnly = true) Level level) {
		return level.inferTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "getContainerSize", at = @At("RETURN"))
	private int modifySize(int original) {
		return this.level().inferTenfoursized() ? 10 * 3 : original;
	}

	@WrapMethod(method = "createMenu")
	private AbstractContainerMenu returnTenfoursizedMenu(int syncId, Inventory inventory, Player player, Operation<AbstractContainerMenu> original) {
		if (inventory.isTenfoursized()) {
			return TenfoursizedContainerMenu.threeRows(syncId, inventory, (ChestBoat) (Object) this);
		} else {
			return original.call(syncId, inventory, player);
		}
	}
}
