package io.github.ennuil.ennuis_bigger_inventories.mixin.core;

import com.mojang.serialization.Codec;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.inventory.SlotRange;
import net.minecraft.world.inventory.SlotRanges;

@Mixin(SlotRanges.class)
public interface SlotRangesAccessor {
	@Accessor("SLOTS")
	@Final
	@Mutable
	static List<SlotRange> getSlots() {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("SLOTS")
	@Final
	@Mutable
	static void setSlots(List<SlotRange> slots) {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("CODEC")
	@Final
	@Mutable
	static void setCodec(Codec<SlotRange> codec) {
		throw new IllegalStateException("Mixin injection failed");
	}

	@Accessor("NAME_LOOKUP")
	@Final
	@Mutable
	static void setNameLookup(Function<String, SlotRange> nameLookup) {
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
