package io.github.ennuil.ennuis_bigger_inventories.impl.screen;

import com.mojang.logging.LogUtils;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.WorldSaveStorageSessionExtensions;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.pack.VanillaDataPackProvider;
import net.minecraft.text.Text;
import net.minecraft.util.CommonColors;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.storage.WorldSaveStorage;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.slf4j.Logger;

@ClientOnly
public class ConvertToTenfoursizedWorldScreen extends Screen {
	private static final Logger LOGGER = LogUtils.getLogger();

	private final BooleanConsumer callback;
	private final DynamicRegistryManager.Frozen frozen;
	private final SaveProperties saveProperties;
	private final WorldSaveStorage.Session session;

	@Nullable
	public static ConvertToTenfoursizedWorldScreen create(MinecraftClient client, BooleanConsumer callback, WorldSaveStorage.Session session) {
		try (var worldStem = client.createIntegratedServerLoader().method_54610(session.method_54545(), false, VanillaDataPackProvider.createPackManager(session))) {
			var saveProperties = worldStem.saveProperties();
			var frozen = worldStem.registries().getCompositeManager();

			return new ConvertToTenfoursizedWorldScreen(callback, frozen, saveProperties, session);
		} catch (Exception e) {
			LOGGER.warn("Failed to load datapacks, can't convert world", e);
			return null;
		}
	}

	public ConvertToTenfoursizedWorldScreen(BooleanConsumer callback, DynamicRegistryManager.Frozen frozen, SaveProperties saveProperties, WorldSaveStorage.Session session) {
		super(Text.translatable("ennuis_bigger_inventories.expand_all_inventories.title", saveProperties.getWorldName()));
		this.callback = callback;
		this.frozen = frozen;
		this.saveProperties = saveProperties;
		this.session = session;
	}

	@Override
	protected void init() {
		((WorldSaveStorageSessionExtensions) this.session).ebi$backupLevelDatFileAndTenfoursize(frozen, saveProperties);
		this.callback.accept(true);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredShadowedText(this.textRenderer, this.title, this.width / 2, this.height / 2, CommonColors.WHITE);
		super.render(graphics, mouseX, mouseY, delta);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}
}
