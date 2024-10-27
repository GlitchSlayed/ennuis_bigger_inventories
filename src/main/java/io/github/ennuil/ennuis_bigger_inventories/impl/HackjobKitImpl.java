package io.github.ennuil.ennuis_bigger_inventories.impl;

import io.github.ennuil.ennuis_bigger_inventories.mixin.core.SlotRangesAccessor;
import java.util.ArrayList;
import net.minecraft.Util;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.SlotRange;

public class HackjobKitImpl {
	public static class TenfoursizedProperty {
		static boolean instance;

		public static boolean getInstance() {
			return instance;
		}

		public static void setInstance(boolean instance) {
			TenfoursizedProperty.instance = instance;
			// Also recreate the SlotRanges in order to shrink/expand it back to normal
			SlotRangesAccessor.setSlots(Util.make(new ArrayList<>(), SlotRangesAccessor::callMethod_58084));
			SlotRangesAccessor.setCodec(StringRepresentable.fromValues(SlotRangesAccessor::callMethod_58090));
			SlotRangesAccessor.setNameLookup(StringRepresentable.createNameLookup(
				SlotRangesAccessor.getSlots().toArray(new SlotRange[0]), name -> name
			));
		}
	}
}
