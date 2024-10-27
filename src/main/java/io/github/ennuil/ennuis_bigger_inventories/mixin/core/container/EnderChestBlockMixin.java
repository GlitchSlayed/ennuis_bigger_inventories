package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.TenfoursizedContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.OptionalInt;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.level.block.EnderChestBlock;

@Mixin(EnderChestBlock.class)
public abstract class EnderChestBlockMixin {
	@Shadow
	@Final
	private static Component CONTAINER_TITLE;

	@WrapOperation(
		method = "useWithoutItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;"
		)
	)
	private OptionalInt openTenfoursizedMenu(Player instance, MenuProvider factory, Operation<OptionalInt> original, @Local(argsOnly = true) Player player, @Local PlayerEnderChestContainer container) {
		if (player.level().inferTenfoursized()) {
			return player.openMenu(
				new SimpleMenuProvider(
					(syncId, inventory, player2) -> TenfoursizedContainerMenu.threeRows(syncId, inventory, container), CONTAINER_TITLE
				)
			);
		} else {
			return original.call(instance, factory);
		}
	}
}
