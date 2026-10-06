package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	@Nullable
	public MultiPlayerGameMode gameMode;

	@Shadow
	@Final
	public Options options;

	@ModifyExpressionValue(method = "handleKeybinds", at = @At(value = "CONSTANT", args = "intValue=9"))
	private int modifyNine(int original) {
		return this.gameMode.isTenfoursized() ? 10 : original;
	}

}
