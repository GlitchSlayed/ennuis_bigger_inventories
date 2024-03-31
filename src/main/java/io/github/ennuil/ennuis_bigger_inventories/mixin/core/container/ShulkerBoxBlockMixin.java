package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin {
	@ModifyExpressionValue(method = "appendTooltip", at = @At(value = "CONSTANT", args = "intValue=27"))
	private int modifyInventorySize(int original, @Local(argsOnly = true) BlockView world) {
		// TODO - If a bug report emerges,
		// Threaten me with a Nullable World and I threaten them with a hackjob, of the client type even!
		// Please let me know if appendTooltip is ever, *ever* called outside of a client, 'cause uh,
		// it would mean a lot of pain to replace this..
		/*
		if (world == null) {
			// I only don't do this because MinecraftClient *cannot* exist on the server at all
			//return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? 30 : original;
		}
		*/

		return ((World) world).inferTenfoursized() ? 30 : original;
	}
}
