package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.serialization.Dynamic;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.server.Main;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.dedicated.ServerPropertiesLoader;
import net.minecraft.world.WorldInfo;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// This mixin will set the default to always tenfoursize the new server world, with no config option whatsoever,
// because well, if you have Big Inv mod on the server, you want to always create a Big Inv world!
// If you somehow want the default to create new 9x4 worlds on a server? I don't know
@Mixin(Main.class)
public abstract class MainMixin {
	@Shadow
	@Final
	private static Logger LOGGER;

	// TODO - Replace me with a MixinExtras 0.4.0 @Share! At least when that happens!
	@Unique
	private static OptionSpec<Void> expandAllInventoriesOptionSpec;

	@ModifyExpressionValue(method = "method_43613", at = @At(value = "NEW", target = "(Ljava/lang/String;Lnet/minecraft/world/GameMode;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/GameRules;Lnet/minecraft/server/world/FeatureAndDataSettings;)Lnet/minecraft/world/WorldInfo;"))
	private static WorldInfo tenfoursizeServerWorld(WorldInfo original) {
		// Also set up the HackjobKit!
		HackjobKitImpl.TenfoursizedProperty.setInstance(true);
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}

	@Inject(method = "main", at = @At(value = "INVOKE", target = "Ljoptsimple/OptionParser;nonOptions()Ljoptsimple/NonOptionArgumentSpec;"), remap = false)
	private static void addExpandAllInventoriesOption(String[] strings, CallbackInfo ci, @Local OptionParser parser, @Share("optionSpec") LocalRef<OptionSpec<Void>> optionSpecRef) {
		expandAllInventoriesOptionSpec = parser.accepts("expandAllInventories", "Expands all inventories to have 10 columns instead of 9. This is irreversible!");
	}

	// You can't inject before method_54523 directly because Mixin hates you
	@Inject(
		method = "method_43613",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/registry/DynamicRegistryManager$Frozen;get(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/registry/Registry;"
		)
	)
	private static void tenfoursizePreexistingSaveProperties(Dynamic<?> dynamic, OptionSet optionSet, OptionSpec<?> optionSpec, ServerPropertiesLoader serverPropertiesLoader, OptionSpec<?> optionSpec2, WorldLoader.DataLoadContext dataLoadContext, CallbackInfoReturnable<WorldLoader.DataLoadOutput<?>> cir, @Local(argsOnly = true) LocalRef<Dynamic<?>> dynamicRef) {
		if (dynamic != null) {
			var tenfoursized = dynamic.get("ennuis_bigger_inventories:is_tenfoursized").asBoolean(false);
			if (optionSet.has(expandAllInventoriesOptionSpec) && !tenfoursized) {
				LOGGER.info("Expanding all inventories!");
				dynamicRef.set(dynamic.set("ennuis_bigger_inventories:is_tenfoursized", dynamic.createBoolean(true)));
			}
		}
	}
}
