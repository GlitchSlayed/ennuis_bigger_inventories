package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBind;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
	@Shadow
	@Final
	@Mutable
	public KeyBind[] hotbarKeys;

	@Shadow
	@Final
	@Mutable
	public KeyBind[] allKeys;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void add10KeyBind(CallbackInfo ci) {
		var tenBind = new KeyBind("key.hotbar.10", GLFW.GLFW_KEY_0, KeyBind.INVENTORY_CATEGORY);

		this.hotbarKeys = ArrayUtils.add(this.hotbarKeys, tenBind);
		this.allKeys = ArrayUtils.add(this.allKeys, tenBind);
	}
}
