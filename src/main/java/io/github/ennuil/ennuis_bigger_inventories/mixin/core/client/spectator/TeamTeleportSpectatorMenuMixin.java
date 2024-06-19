package io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.spectator;

import io.github.ennuil.ennuis_bigger_inventories.impl.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.spectator.TeamTeleportSpectatorMenu;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(TeamTeleportSpectatorMenu.class)
public abstract class TeamTeleportSpectatorMenuMixin {
	@Unique private static final Identifier EBI_TELEPORT_TO_TEAM_TEXTURE = ModUtils.id("spectator/teleport_to_team");

	@ModifyArg(
		method = "renderIcon",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private Identifier modifyTeleportToTeamTexture(Identifier original) {
		return MinecraftClient.getInstance().interactionManager.isTenfoursized() ? EBI_TELEPORT_TO_TEAM_TEXTURE : original;
	}
}
