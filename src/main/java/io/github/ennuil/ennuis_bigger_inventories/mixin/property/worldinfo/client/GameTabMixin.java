package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldCreatorExtensions;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(targets = "net/minecraft/client/gui/screen/world/CreateWorldScreen$GameTab")
public abstract class GameTabMixin {
	@Unique
	private static final Text EXPAND_INVENTORIES = Text.translatable("selectWorld.ennuis_bigger_inventories.expand_inventories");

	@Unique
	private static final Text EXPAND_INVENTORIES_INFO = Text.translatable("selectWorld.ennuis_bigger_inventories.expand_inventories.info");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addTenfoursizeButton(CreateWorldScreen screen, CallbackInfo ci, @Local(ordinal = 0) GridWidget.AdditionHelper additionHelper) {
		var tenfoursizeButton = additionHelper.add(
			CyclingButtonWidget.onOffBuilder()
				.tooltip(bool -> Tooltip.create(EXPAND_INVENTORIES_INFO))
				.build(0, 0, 210, 20, EXPAND_INVENTORIES, (button, bool) -> ((WorldCreatorExtensions) screen.getWorldCreator()).ebi$setTenfoursized(bool))
		);
		screen.getWorldCreator().addListener(worldCreator -> {
			tenfoursizeButton.setValue(((WorldCreatorExtensions) screen.getWorldCreator()).ebi$isTenfoursized());
			tenfoursizeButton.active = !screen.getWorldCreator().isDebug();
		});
	}
}
