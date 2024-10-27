package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
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
@Mixin(Options.class)
public abstract class OptionsMixin {
	@Shadow
	@Final
	@Mutable
	public KeyMapping[] keyHotbarSlots;

	@Shadow
	@Final
	@Mutable
	public KeyMapping[] keyMappings;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void add10KeyBind(CallbackInfo ci) {
		var tenBind = new KeyMapping("key.hotbar.10", GLFW.GLFW_KEY_0, KeyMapping.CATEGORY_INVENTORY);

		this.keyHotbarSlots = ArrayUtils.add(this.keyHotbarSlots, tenBind);
		this.keyMappings = ArrayUtils.add(this.keyMappings, tenBind);
	}
}
