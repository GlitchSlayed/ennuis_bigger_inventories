package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEnderChestContainer.class)
public abstract class PlayerEnderChestContainerMixin {
	// A better way would be to somehow give a Player to this hecker and use it to conditionally modify the size,
	// but it would be too risky compared to the hackjob.
	@ModifyArg(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/SimpleContainer;<init>(I)V"
		)
	)
	private static int modifySize(int original) {
        return HackjobKit.isTenfoursized() ? 10 * 3 : original;
    }
}
