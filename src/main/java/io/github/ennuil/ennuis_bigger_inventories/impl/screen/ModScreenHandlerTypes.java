package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.feature_flags.FeatureFlags;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlerTypes {
	public static final ScreenHandlerType<GenericTensizedContainerScreenHandler> GENERIC_10X1 = new ScreenHandlerType<>(GenericTensizedContainerScreenHandler::createGeneric10x1, FeatureFlags.DEFAULT_SET);
	public static final ScreenHandlerType<GenericTensizedContainerScreenHandler> GENERIC_10X2 = new ScreenHandlerType<>(GenericTensizedContainerScreenHandler::createGeneric10x2, FeatureFlags.DEFAULT_SET);
	public static final ScreenHandlerType<GenericTensizedContainerScreenHandler> GENERIC_10X3 = new ScreenHandlerType<>(GenericTensizedContainerScreenHandler::createGeneric10x3, FeatureFlags.DEFAULT_SET);
	public static final ScreenHandlerType<GenericTensizedContainerScreenHandler> GENERIC_10X4 = new ScreenHandlerType<>(GenericTensizedContainerScreenHandler::createGeneric10x4, FeatureFlags.DEFAULT_SET);
	public static final ScreenHandlerType<GenericTensizedContainerScreenHandler> GENERIC_10X5 = new ScreenHandlerType<>(GenericTensizedContainerScreenHandler::createGeneric10x5, FeatureFlags.DEFAULT_SET);
	public static final ScreenHandlerType<GenericTensizedContainerScreenHandler> GENERIC_10X6 = new ScreenHandlerType<>(GenericTensizedContainerScreenHandler::createGeneric10x6, FeatureFlags.DEFAULT_SET);

	public static void register() {
		Registry.register(Registries.SCREEN_HANDLER_TYPE, ModUtils.id("generic_10x1"), GENERIC_10X1);
		Registry.register(Registries.SCREEN_HANDLER_TYPE, ModUtils.id("generic_10x2"), GENERIC_10X2);
		Registry.register(Registries.SCREEN_HANDLER_TYPE, ModUtils.id("generic_10x3"), GENERIC_10X3);
		Registry.register(Registries.SCREEN_HANDLER_TYPE, ModUtils.id("generic_10x4"), GENERIC_10X4);
		Registry.register(Registries.SCREEN_HANDLER_TYPE, ModUtils.id("generic_10x5"), GENERIC_10X5);
		Registry.register(Registries.SCREEN_HANDLER_TYPE, ModUtils.id("generic_10x6"), GENERIC_10X6);
	}
}
