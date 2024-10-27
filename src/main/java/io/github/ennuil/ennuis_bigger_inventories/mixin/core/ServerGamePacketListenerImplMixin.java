package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
	@Shadow
	public ServerPlayer player;

	@ModifyExpressionValue(method = "handleEditBook", at = @At(value = "CONSTANT", args = "intValue=40"))
	private int modify40(int original) {
		return this.player.serverLevel().isTenfoursized() ? 40 + 4 : original;
	}

	@ModifyExpressionValue(method = "handleSetCarriedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getSelectionSize()I"))
	private int modifyGetSelectionSize(int original) {
		return this.player.serverLevel().isTenfoursized() ? 10 : original;
	}

	// 45 is 9 * 4 (36) + 9!
	@ModifyExpressionValue(method = "handleSetCreativeModeSlot", at = @At(value = "CONSTANT", args = "intValue=45"))
	private int modify45(int original) {
		return this.player.serverLevel().isTenfoursized() ? 9 + 10 * 4 : original;
	}
}
