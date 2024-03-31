package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BarrelBlockEntity.class)
public abstract class BarrelBlockEntityMixin {
	@Shadow
	private DefaultedList<ItemStack> inventory;

	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/collection/DefaultedList;ofSize(ILjava/lang/Object;)Lnet/minecraft/util/collection/DefaultedList;"
		)
	)
	private int modifySize(int original) {
		return HackjobKit.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "size", at = @At("RETURN"))
	private int modifyReturnedSize(int original) {
		return this.inventory.size();
	}

	@ModifyReturnValue(method = "createScreenHandler", at = @At(value = "RETURN"))
	private ScreenHandler return10x3ScreenHandler(ScreenHandler original, int syncId, PlayerInventory playerInventory) {
		if (playerInventory.isTenfoursized()) {
			return GenericTensizedContainerScreenHandler.createGeneric10x3(syncId, playerInventory, (BarrelBlockEntity) (Object) this);
		} else {
			return original;
		}
	}
}
