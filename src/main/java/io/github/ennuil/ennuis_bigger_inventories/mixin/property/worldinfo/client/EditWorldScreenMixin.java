package io.github.ennuil.ennuis_bigger_inventories.mixin.property.worldinfo.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelSummaryExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.screen.ConvertToTenfoursizedWorldScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Environment(EnvType.CLIENT)
@Mixin(EditWorldScreen.class)
public abstract class EditWorldScreenMixin extends Screen {
	@Shadow
	@Final
	private LevelStorageSource.LevelStorageAccess levelAccess;

	private EditWorldScreenMixin(Component title) {
		super(title);
	}

	@Shadow
	@Final
	private LinearLayout layout;

	@Shadow
	@Final
	private BooleanConsumer callback;

	@Shadow
	public static boolean makeBackupAndShowToast(LevelStorageSource.LevelStorageAccess storageSession) {
		return false;
	}

	@Inject(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/layouts/LinearLayout;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;",
			ordinal = 10
		)
	)
	private void addConvertToTenfoursizedButton(CallbackInfo ci) throws IOException {
		if (!((LevelSummaryExtensions) levelAccess.getSummary(levelAccess.getDataTag())).ebi$isTenfoursized()) {
			this.layout.spacing(3);
			this.layout.addChild(
				Button.builder(Component.translatable("selectWorld.ennuis_bigger_inventories.edit.expand_all_inventories"), button -> this.minecraft.setScreen(new BackupConfirmScreen(() -> minecraft.setScreen(this), (backup, eraseCache) -> {
						if (backup) {
							makeBackupAndShowToast(this.levelAccess);
						}

						this.minecraft.setScreen(ConvertToTenfoursizedWorldScreen.create(this.minecraft, this.callback, this.levelAccess));
					}, Component.translatable("ennuis_bigger_inventories.expand_all_inventories.confirm.title"), Component.translatable("ennuis_bigger_inventories.expand_all_inventories.confirm.description"), false)))
					.width(200)
					.build()
			);
		}
	}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/SpacerElement;<init>(II)V"), index = 1)
	private int modifyHeight(int original) throws IOException {
		if (!((LevelSummaryExtensions) levelAccess.getSummary(levelAccess.getDataTag())).ebi$isTenfoursized()) {
			return 0;
		} else {
			return original;
		}
	}
}
