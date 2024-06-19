package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSaveSummaryExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ConvertToTenfoursizedWorldScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.world.storage.WorldSaveStorage;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@ClientOnly
@Mixin(EditWorldScreen.class)
public abstract class EditWorldScreenMixin extends Screen {
	@Shadow
	@Final
	private WorldSaveStorage.Session storageSession;

	private EditWorldScreenMixin(Text title) {
		super(title);
	}

	@Shadow
	public static boolean createBackup(WorldSaveStorage.Session storageSession) {
		return false;
	}

	@Shadow
	@Final
	private BooleanConsumer callback;

	@Inject(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/widget/layout/LinearLayoutWidget;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;",
			ordinal = 10
		)
	)
	private void addConvertToTenfoursizedButton(CallbackInfo ci) {
		try {
			if (!((WorldSaveSummaryExtensions) storageSession.getWorldSaveSummary(storageSession.method_54545())).ebi$isTenfoursized()) {
				this.addDrawableSelectableElement(
					ButtonWidget.builder(Text.translatable("selectWorld.ennuis_bigger_inventories.edit.expand_all_inventories"), button -> this.client.setScreen(new BackupPromptScreen(() -> client.setScreen(this), (backup, eraseCache) -> {
						if (backup) {
							createBackup(this.storageSession);
						}

						this.client.setScreen(ConvertToTenfoursizedWorldScreen.create(this.client, this.callback, this.storageSession));
					}, Text.translatable("ennuis_bigger_inventories.expand_all_inventories.confirm.title"), Text.translatable("ennuis_bigger_inventories.expand_all_inventories.confirm.description"), false)))
					.positionAndSize(this.width / 2 - 100, this.height / 4 + 120 + 5, 200, 20)
					.build()
				);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
