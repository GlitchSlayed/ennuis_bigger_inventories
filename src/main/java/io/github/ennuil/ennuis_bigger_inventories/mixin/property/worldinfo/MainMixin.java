package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import net.minecraft.server.Main;
import net.minecraft.world.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// This mixin will set the default to always tenfoursize the new server world, with no config option whatsoever,
// because well, if you have Big Inv mod on the server, you want to always create a Big Inv world!
// If you somehow want the default to create new 9x4 worlds on a server? I don't know
@Mixin(Main.class)
public abstract class MainMixin {
	@ModifyExpressionValue(method = "method_43613", at = @At(value = "NEW", target = "(Ljava/lang/String;Lnet/minecraft/world/GameMode;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/GameRules;Lnet/minecraft/server/world/FeatureAndDataSettings;)Lnet/minecraft/world/WorldInfo;"))
	private static WorldInfo tenfoursizeServerWorld(WorldInfo original) {
		// Also set up the HackjobKit!
		HackjobKitImpl.TenfoursizedProperty.setInstance(true);
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}
}
