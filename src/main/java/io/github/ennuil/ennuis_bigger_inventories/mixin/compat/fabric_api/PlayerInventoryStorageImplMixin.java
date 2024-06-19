package io.github.ennuil.ennuis_bigger_inventories.mixin.compat.fabric_api;

import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net/fabricmc/fabric/impl/transfer/item/PlayerInventoryStorageImpl")
public abstract class PlayerInventoryStorageImplMixin {
	@ModifyArg(method = "offer", at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;"), remap = false)
	private int modifyMainSize(int original) {
		return HackjobKit.isTenfoursized() ? 40 : original;
	}
}
