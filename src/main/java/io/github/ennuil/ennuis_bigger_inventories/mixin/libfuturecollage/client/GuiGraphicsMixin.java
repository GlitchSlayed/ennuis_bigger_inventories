package io.github.ennuil.ennuis_bigger_inventories.mixin.libfuturecollage.client;

import io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage.SplitTextureGuiGraphics;
import io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage.SplitTextureSprite;
import io.github.ennuil.ennuis_bigger_inventories.impl.libfuturecollage.reloaders.GuiAtlasManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements SplitTextureGuiGraphics {
	@Shadow
	public abstract void drawSprite(int x, int y, int z, int width, int height, Sprite sprite);

	@Shadow
	abstract void drawTexturedQuad(Identifier texture, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2);

	@Unique
	private GuiAtlasManager guiAtlasManager;

	@Inject(
		method = "<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;)V",
		at = @At("TAIL")

	)
	private void getGuiAtlas(MinecraftClient client, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumerProvider, CallbackInfo ci) {
		this.guiAtlasManager = GuiAtlasManager.getInstance();
	}

	@Override
	public void drawGuiTexture(Identifier texture, int x, int y, int width, int height) {
		drawGuiTexture(texture, x, y, 0, width, height);
	}

	@Override
	public void drawGuiTexture(Identifier texture, int x, int y, int z, int width, int height) {
		var sprite = this.guiAtlasManager.getSprite(texture);
		this.drawSprite(x, y, z, width, height, sprite);
	}

	@Override
	public void drawGuiTexture(Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int width, int height) {
		drawGuiTexture(texture, sliceWidth1, sliceHeight1, sliceWidth2, sliceHeight2, x, y, 0, width, height);
	}

	@Override
	public void drawGuiTexture(Identifier texture, int sliceWidth1, int sliceHeight1, int sliceWidth2, int sliceHeight2, int x, int y, int z, int width, int height) {
		var sprite = this.guiAtlasManager.getSprite(texture);
		if (width != 0 && height != 0) {
			this.drawTexturedQuad(
				sprite.getId(),
				x,
				x + width,
				y,
				y + height,
				z,
				((SplitTextureSprite) sprite).lfc$getFrameU(((float) sliceWidth2 / (float) sliceWidth1)),
				((SplitTextureSprite) sprite).lfc$getFrameU((float) (sliceWidth2 + width) / (float) sliceWidth1),
				((SplitTextureSprite) sprite).lfc$getFrameV((float) sliceHeight2 / (float) sliceHeight1),
				((SplitTextureSprite) sprite).lfc$getFrameV((float) (sliceHeight2 + height) / (float) sliceHeight1)
			);
		}
	}
}
