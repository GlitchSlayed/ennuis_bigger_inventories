package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldCreationUiStateExtensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net/minecraft/client/gui/screens/worldselection/CreateWorldScreen$GameTab")
public abstract class GameTabMixin {
	@Unique
	private static final Component EXPAND_INVENTORIES = Component.translatable("selectWorld.ennuis_bigger_inventories.expand_inventories");

	@Unique
	private static final Component EXPAND_INVENTORIES_INFO = Component.translatable("selectWorld.ennuis_bigger_inventories.expand_inventories.info");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addTenfoursizeButton(CreateWorldScreen screen, CallbackInfo ci, @Local(ordinal = 0) GridLayout.RowHelper additionHelper) {
		var tenfoursizeButton = additionHelper.addChild(
			CycleButton.onOffBuilder()
				.withTooltip(bool -> Tooltip.create(EXPAND_INVENTORIES_INFO))
				.create(0, 0, 210, 20, EXPAND_INVENTORIES, (button, bool) -> ((WorldCreationUiStateExtensions) screen.getUiState()).ebi$setTenfoursized(bool))
		);
		screen.getUiState().addListener(worldCreator -> {
			tenfoursizeButton.setValue(((WorldCreationUiStateExtensions) screen.getUiState()).ebi$isTenfoursized());
			tenfoursizeButton.active = !screen.getUiState().isDebug();
		});
	}
}
