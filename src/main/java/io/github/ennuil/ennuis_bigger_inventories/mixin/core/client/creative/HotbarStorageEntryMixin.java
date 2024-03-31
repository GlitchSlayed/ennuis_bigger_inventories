package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.option.HotbarStorageEntry;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(HotbarStorageEntry.class)
public abstract class HotbarStorageEntryMixin {
	@ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getHotbarSize()I"))
	private int modifyGetHotbarSize(int original) {
		return 10;
	}
}
