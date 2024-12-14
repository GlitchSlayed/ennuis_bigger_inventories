package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.inventory.Hotbar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(Hotbar.class)
public abstract class HotbarMixin {
	@ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getSelectionSize()I"))
	private static int modifyGetSelectionSize(int original) {
		return 10;
	}

	@ModifyArg(method = "storeFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getItem(I)Lnet/minecraft/world/item/ItemStack;"))
	private int modifyGetItemToOffHand(int original) {
		if (original == 9 && !Minecraft.getInstance().gameMode.isTenfoursized()) {
			return 40;
		}

		return original;
	}
}
