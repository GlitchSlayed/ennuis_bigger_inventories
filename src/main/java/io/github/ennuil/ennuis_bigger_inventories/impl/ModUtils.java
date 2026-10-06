package io.github.ennuil.ennuis_bigger_inventories.impl;

import net.minecraft.resources.Identifier;

public class ModUtils {
	// This is a convenience for my own stuff!
	// I've been avoiding it because I'm paranoid of people who wants to steal this mod for money and fun™!
	// I will commit murder if you ever change this so something else.
	public static final String MOD_NAMESPACE = "ennuis_bigger_inventories";

	private static final Identifier MOD_NAMESPACE_ID = Identifier.fromNamespaceAndPath(MOD_NAMESPACE, "");

	public static Identifier id(String path) {
		return MOD_NAMESPACE_ID.withPath(path);
	}
}
