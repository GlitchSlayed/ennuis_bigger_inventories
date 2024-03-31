package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	@ModifyExpressionValue(method = "onBookUpdate", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original) {
		return this.player.getServerWorld().isTenfoursized() ? 40 + 4 : original;
	}

	@ModifyExpressionValue(method = "onSelectedSlotUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getHotbarSize()I"))
	private int modifyGetHotbarSize(int original) {
		return this.player.getServerWorld().isTenfoursized() ? 10 : original;
	}

	// 45 is 9 * 4 (36) + 9!
	@ModifyExpressionValue(method = "onCreativeInventoryAction", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original) {
		return this.player.getServerWorld().isTenfoursized() ? 9 + 10 * 4 : original;
	}
}
