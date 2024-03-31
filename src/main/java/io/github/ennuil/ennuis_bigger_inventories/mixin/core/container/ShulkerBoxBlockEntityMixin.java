package io.github.ennuil.ennuis_bigger_inventories.mixin.core.container;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.ennuil.ennuis_bigger_inventories.api.HackjobKit;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.stream.IntStream;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class ShulkerBoxBlockEntityMixin {
	@Shadow
	public abstract int size();

	@Unique
	private static final int[] AVAILABLE_TENFOURSIZED_SLOTS = IntStream.range(0, 30).toArray();

	// This is horrible but really, the other way around is worse
	// TODO - Keep a watch on the hackjob and see if it works well
	@ModifyArg(
		method = {
			"<init>(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
			"<init>(Lnet/minecraft/util/DyeColor;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"
		},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/collection/DefaultedList;ofSize(ILjava/lang/Object;)Lnet/minecraft/util/collection/DefaultedList;"
		),
		index = 0
	)
	private int modifyDefaultedListSize(int original) {
		return HackjobKit.isTenfoursized() ? 10 * 3 : original;
	}

	@ModifyReturnValue(method = "getAvailableSlots", at = @At("RETURN"))
	private int[] modifyAvailableSlots(int[] original) {
		return this.size() == 30 ? AVAILABLE_TENFOURSIZED_SLOTS : original;
	}
}
