package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BarrelBlockEntity.class)
public abstract class BarrelBlockEntityMixin {
	@Shadow
	private NonNullList<ItemStack> items;

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"
		)
	)
	private int modifySize(int original) {
		return HackjobKit.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "getContainerSize", at = @At("RETURN"))
	private int modifyReturnedSize(int original) {
		return this.items.size();
	}

	@WrapMethod(method = "createMenu")
	private AbstractContainerMenu returnTenfoursizedMenu(int syncId, Inventory inventory, Operation<AbstractContainerMenu> original) {
		if (inventory.isTenfoursized()) {
			return TenfoursizedContainerMenu.threeRows(syncId, inventory, (BarrelBlockEntity) (Object) this);
		} else {
			return original.call(syncId, inventory);
		}
	}
}
