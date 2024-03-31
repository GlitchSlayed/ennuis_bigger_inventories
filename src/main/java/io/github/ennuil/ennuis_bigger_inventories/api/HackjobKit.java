package io.github.ennuil.ennuis_bigger_inventories.api;

import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;

/**
 * Holds all sorts of nasty hackjobs necessary in order to avoid other worse hackjobs
 */
public class HackjobKit {
	public static boolean isTenfoursized() {
		return HackjobKitImpl.TenfoursizedProperty.getInstance();
	}
}
