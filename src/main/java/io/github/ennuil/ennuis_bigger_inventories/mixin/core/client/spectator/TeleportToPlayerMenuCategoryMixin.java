package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayerMenuCategory;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(TeleportToPlayerMenuCategory.class)
public abstract class TeleportToPlayerMenuCategoryMixin {
	@Unique private static final ResourceLocation EBI_TELEPORT_TO_PLAYER_SPRITE = ModUtils.id("spectator/teleport_to_player");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V"
		)
	)
	private ResourceLocation modifyTeleportToPlayerTexture(ResourceLocation original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_TELEPORT_TO_PLAYER_SPRITE : original;
	}
}
