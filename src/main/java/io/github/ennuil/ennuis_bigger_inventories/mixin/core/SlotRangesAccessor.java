package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.mojang.serialization.Codec;
import net.minecraft.inventory.SlotRange;
import net.minecraft.inventory.SlotRanges;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mixin(SlotRanges.class)
public interface SlotRangesAccessor {
	@Accessor("SLOT_RANGES")
	@Final
	@Mutable
	static List<SlotRange> getSlotRanges() {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("SLOT_RANGES")
	@Final
	@Mutable
	static void setSlotRanges(List<SlotRange> slotRanges) {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("CODEC")
	@Final
	@Mutable
	static void setCodec(Codec<SlotRange> codec) {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("FROM_NAME")
	@Final
	@Mutable
	static void setFromName(Function<String, SlotRange> function) {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Invoker(remap = false)
	static void callMethod_58084(ArrayList<?> list) {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Invoker(remap = false)
	static SlotRange[] callMethod_58090() {
		throw new IllegalStateException("Mixin injection failed");
	}
}
