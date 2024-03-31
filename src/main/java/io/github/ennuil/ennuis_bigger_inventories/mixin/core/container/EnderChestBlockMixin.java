package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.GenericTensizedContainerScreenHandler;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.OptionalInt;

@Mixin(EnderChestBlock.class)
public abstract class EnderChestBlockMixin {
	@Shadow
	@Final
	private static Text CONTAINER_NAME;

	@WrapOperation(
		method = "onUse",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"
		)
	)
	private OptionalInt openTensizedHandledScreen(PlayerEntity instance, NamedScreenHandlerFactory factory, Operation<OptionalInt> original, @Local(argsOnly = true) PlayerEntity player, @Local EnderChestInventory inventory) {
		if (player.getWorld().inferTenfoursized()) {
			return player.openHandledScreen(
				new SimpleNamedScreenHandlerFactory(
					(syncId, playerInventory, playerEntity) -> GenericTensizedContainerScreenHandler.createGeneric10x3(syncId, playerInventory, inventory), CONTAINER_NAME
				)
			);
		} else {
			return original.call(instance, factory);
		}
	}
}
