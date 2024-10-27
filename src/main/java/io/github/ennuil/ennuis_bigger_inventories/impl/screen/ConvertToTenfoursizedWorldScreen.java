package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import com.mojang.logging.LogUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.LevelStorageAccessExtensions;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.slf4j.Logger;

@ClientOnly
public class ConvertToTenfoursizedWorldScreen extends Screen {
	private static final Logger LOGGER = LogUtils.getLogger();

	private final BooleanConsumer callback;
	private final RegistryAccess.Frozen frozen;
	private final WorldData worldData;
	private final LevelStorageSource.LevelStorageAccess levelStorageAccess;

	@Nullable
	public static ConvertToTenfoursizedWorldScreen create(Minecraft client, BooleanConsumer callback, LevelStorageSource.LevelStorageAccess levelStorageAccess) {
		try (var worldStem = client.createWorldOpenFlows().loadWorldStem(levelStorageAccess.getDataTag(), false, ServerPacksSource.createPackRepository(levelStorageAccess))) {
			var worldData = worldStem.worldData();
			var frozen = worldStem.registries().compositeAccess();

			return new ConvertToTenfoursizedWorldScreen(callback, frozen, worldData, levelStorageAccess);
		} catch (Exception e) {
			LOGGER.warn("Failed to load datapacks, can't convert world", e);
			return null;
		}
	}

	public ConvertToTenfoursizedWorldScreen(BooleanConsumer callback, RegistryAccess.Frozen frozen, WorldData worldData, LevelStorageSource.LevelStorageAccess levelStorageAccess) {
		super(Component.translatable("ennuis_bigger_inventories.expand_all_inventories.title", worldData.getLevelName()));
		this.callback = callback;
		this.frozen = frozen;
		this.worldData = worldData;
		this.levelStorageAccess = levelStorageAccess;
	}

	@Override
	protected void init() {
		((LevelStorageAccessExtensions) this.levelStorageAccess).ebi$backupLevelDataAndTenfoursize(frozen, worldData);
		this.callback.accept(true);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2, CommonColors.WHITE);
		super.render(graphics, mouseX, mouseY, delta);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}
}
