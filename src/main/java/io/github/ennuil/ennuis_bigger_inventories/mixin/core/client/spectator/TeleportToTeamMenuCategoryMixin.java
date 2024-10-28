package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.categories.TeleportToTeamMenuCategory;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(TeleportToTeamMenuCategory.class)
public abstract class TeleportToTeamMenuCategoryMixin {
	@Unique private static final ResourceLocation EBI_TELEPORT_TO_TEAM_SPRITE = ModUtils.id("spectator/teleport_to_team");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V"
		)
	)
	private ResourceLocation modifyTeleportToTeamTexture(ResourceLocation original) {
		return Minecraft.getInstance().gameMode.isTenfoursized() ? EBI_TELEPORT_TO_TEAM_SPRITE : original;
	}
}
