package io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage.reloaders;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.resource.loader.api.reloader.SimpleResourceReloader;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@ClientOnly
public class GuiAtlasManager implements SimpleResourceReloader<SpriteLoader.SpritePreparations> {
	private final SpriteAtlasTexture atlas;
	public static final Identifier RELOADER_ID = new Identifier("libfuturecollage", "gui_atlas_manager");

	private static final Identifier RESOURCE_ID = new Identifier("libfuturecollage", "gui");
	private static final Identifier ATLAS_ID = new Identifier("libfuturecollage", "textures/atlas/gui.png");
	private static GuiAtlasManager instance;

	public GuiAtlasManager(TextureManager textureManager) {
		this.atlas = new SpriteAtlasTexture(ATLAS_ID);
		textureManager.registerTexture(this.atlas.getId(), this.atlas);
		instance = this;
	}

	public static GuiAtlasManager getInstance() {
		return instance;
	}

	public Sprite getSprite(Identifier id) {
		return this.atlas.getSprite(id);
	}

	@Override
	public CompletableFuture<SpriteLoader.SpritePreparations> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return SpriteLoader.create(this.atlas)
			.loadAndStitch(manager, RESOURCE_ID, 0, executor)
			.thenCompose(SpriteLoader.SpritePreparations::waitForUpload);
	}

	@Override
	public CompletableFuture<Void> apply(SpriteLoader.SpritePreparations preparations, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.runAsync(() -> this.upload(preparations, profiler), executor);
	}

	private void upload(SpriteLoader.SpritePreparations preparations, Profiler profiler) {
		profiler.startTick();
		profiler.push("upload");
		this.atlas.upload(preparations);
		profiler.pop();
		profiler.endTick();
	}

	@Override
	public @NotNull Identifier getQuiltId() {
		return RELOADER_ID;
	}
}
