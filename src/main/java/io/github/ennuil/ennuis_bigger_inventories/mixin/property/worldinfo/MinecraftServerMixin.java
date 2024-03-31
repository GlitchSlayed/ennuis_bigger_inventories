package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@ModifyExpressionValue(method = "<clinit>", at = @At(value = "NEW", target = "(Ljava/lang/String;Lnet/minecraft/world/GameMode;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/GameRules;Lnet/minecraft/server/world/FeatureAndDataSettings;)Lnet/minecraft/world/WorldInfo;"))
	private static WorldInfo tenfoursizeDemoWorld(WorldInfo original) {
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}
}
