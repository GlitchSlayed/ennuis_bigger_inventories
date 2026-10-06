package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayerMenuCategory;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(TeleportToPlayerMenuCategory.class)
public abstract class TeleportToPlayerMenuCategoryMixin {
	@Unique private static final Identifier EBI_TELEPORT_TO_PLAYER_SPRITE = ModUtils.id("spectator/teleport_to_player");

	@ModifyArg(
		method = "extractIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"
		)
	)
	private Identifier modifyTeleportToPlayerTexture(Identifier original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_TELEPORT_TO_PLAYER_SPRITE : original;
	}
}
