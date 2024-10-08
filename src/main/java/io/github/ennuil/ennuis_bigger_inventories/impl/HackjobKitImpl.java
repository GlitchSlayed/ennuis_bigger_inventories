package io.github.ennuil.ennuis_bigger_inventories.impl;

import io.github.ennuil.ennuis_bigger_inventories.mixin.core.SlotRangesAccessor;
import net.minecraft.inventory.SlotRange;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

import java.util.ArrayList;

public class HackjobKitImpl {
	public static class TenfoursizedProperty {
		static boolean instance;

		public static boolean getInstance() {
			return instance;
		}

		public static void setInstance(boolean instance) {
			TenfoursizedProperty.instance = instance;
			// Also recreate the SlotRanges in order to shrink/expand it back to normal
			SlotRangesAccessor.setSlotRanges(Util.make(new ArrayList<>(), SlotRangesAccessor::callMethod_58084));
			SlotRangesAccessor.setCodec(StringIdentifiable.createCodec(SlotRangesAccessor::callMethod_58090));
			SlotRangesAccessor.setFromName(StringIdentifiable.stringToElementFunction(
				SlotRangesAccessor.getSlotRanges().toArray(new SlotRange[0]), name -> name
			));
		}
	}
}
