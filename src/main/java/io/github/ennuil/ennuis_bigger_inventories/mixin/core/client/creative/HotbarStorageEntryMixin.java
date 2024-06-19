package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.HotbarStorageEntry;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(HotbarStorageEntry.class)
public abstract class HotbarStorageEntryMixin {
	@ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getHotbarSize()I"))
	private static int modifyGetHotbarSize(int original) {
		return 10;
	}

	@ModifyArg(method = "method_56836", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getStack(I)Lnet/minecraft/item/ItemStack;"))
	private int modifyGetStackToOffHand(int original) {
		if (original == 9 && !MinecraftClient.getInstance().interactionManager.isTenfoursized()) {
			return 40;
		}

		return original;
	}
}
