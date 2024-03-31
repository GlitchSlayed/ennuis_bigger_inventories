package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldInfoExtensions;
import net.minecraft.test.TestServer;
import net.minecraft.world.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TestServer.class)
public abstract class TestServerMixin {
	@ModifyExpressionValue(method = "create", at = @At(value = "NEW", target = "(Ljava/lang/String;Lnet/minecraft/world/GameMode;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/GameRules;Lnet/minecraft/server/world/FeatureAndDataSettings;)Lnet/minecraft/world/WorldInfo;"))
	private static WorldInfo tenfoursizeTestServerWorld(WorldInfo original) {
		((WorldInfoExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}
}
