package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
	public static final MenuType<TenfoursizedContainerMenu> GENERIC_10X1 = new MenuType<>(TenfoursizedContainerMenu::oneRow, FeatureFlags.VANILLA_SET);
	public static final MenuType<TenfoursizedContainerMenu> GENERIC_10X2 = new MenuType<>(TenfoursizedContainerMenu::twoRows, FeatureFlags.VANILLA_SET);
	public static final MenuType<TenfoursizedContainerMenu> GENERIC_10X3 = new MenuType<>(TenfoursizedContainerMenu::threeRows, FeatureFlags.VANILLA_SET);
	public static final MenuType<TenfoursizedContainerMenu> GENERIC_10X4 = new MenuType<>(TenfoursizedContainerMenu::fourRows, FeatureFlags.VANILLA_SET);
	public static final MenuType<TenfoursizedContainerMenu> GENERIC_10X5 = new MenuType<>(TenfoursizedContainerMenu::fiveRows, FeatureFlags.VANILLA_SET);
	public static final MenuType<TenfoursizedContainerMenu> GENERIC_10X6 = new MenuType<>(TenfoursizedContainerMenu::sixRows, FeatureFlags.VANILLA_SET);

	public static void register() {
		Registry.register(BuiltInRegistries.MENU, ModUtils.id("generic_10x1"), GENERIC_10X1);
		Registry.register(BuiltInRegistries.MENU, ModUtils.id("generic_10x2"), GENERIC_10X2);
		Registry.register(BuiltInRegistries.MENU, ModUtils.id("generic_10x3"), GENERIC_10X3);
		Registry.register(BuiltInRegistries.MENU, ModUtils.id("generic_10x4"), GENERIC_10X4);
		Registry.register(BuiltInRegistries.MENU, ModUtils.id("generic_10x5"), GENERIC_10X5);
		Registry.register(BuiltInRegistries.MENU, ModUtils.id("generic_10x6"), GENERIC_10X6);
	}
}
