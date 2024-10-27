package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.stream.IntStream;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class ShulkerBoxBlockEntityMixin {
	@Shadow
	public abstract int getContainerSize();

	@Unique
	private static final int[] AVAILABLE_TENFOURSIZED_SLOTS = IntStream.range(0, 30).toArray();

	// This is horrible but really, the other way around is worse
	// TODO - Keep a watch on the hackjob and see if it works well
	@ModifyArg(
		method = {
			"<init>(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
			"<init>(Lnet/minecraft/world/item/DyeColor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"
		)
	)
	private int modifyDefaultedListSize(int original) {
		return HackjobKit.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "getSlotsForFace", at = @At("RETURN"))
	private int[] modifyAvailableSlots(int[] original) {
		return this.getContainerSize() == 30 ? AVAILABLE_TENFOURSIZED_SLOTS : original;
	}
}
