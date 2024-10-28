package io.github.ennuil.ennuis_bigger_inventories.mixin.core.station;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {
	// Quick Transfer
	@ModifyExpressionValue(method = "quickMoveStack", at = @At(value = "CONSTANT", args = "intValue=38"))
	private int modify38(int original, Player player) {
		return player.level().inferTenfoursized() ? 2 + 10 * 4 : original;
	}
}
