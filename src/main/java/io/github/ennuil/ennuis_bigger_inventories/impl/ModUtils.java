package io.github.ennuil.ennuis_bigger_inventories.impl;

import net.minecraft.resources.ResourceLocation;

public class ModUtils {
	// This is a convenience for my own stuff!
	// I've been avoiding it because I'm paranoid of people who wants to steal this mod for money and fun™!
	// I will commit murder if you ever change this so something else.
	public static final String MOD_NAMESPACE = "ennuis_bigger_inventories";

	private static final ResourceLocation MOD_NAMESPACE_ID = ResourceLocation.fromNamespaceAndPath(MOD_NAMESPACE, "");

	public static ResourceLocation id(String path) {
		return MOD_NAMESPACE_ID.withPath(path);
	}
}
