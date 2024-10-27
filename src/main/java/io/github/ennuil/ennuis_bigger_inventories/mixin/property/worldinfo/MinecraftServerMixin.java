package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSettingsExtensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@ModifyExpressionValue(method = "<clinit>", at = @At(value = "NEW", target = "(Ljava/lang/String;Lnet/minecraft/world/level/GameType;ZLnet/minecraft/world/Difficulty;ZLnet/minecraft/world/level/GameRules;Lnet/minecraft/world/level/WorldDataConfiguration;)Lnet/minecraft/world/level/LevelSettings;"))
	private static LevelSettings tenfoursizeDemoLevel(LevelSettings original) {
		((LevelSettingsExtensions) (Object) original).ebi$setTenfoursized(true);
		return original;
	}
}
