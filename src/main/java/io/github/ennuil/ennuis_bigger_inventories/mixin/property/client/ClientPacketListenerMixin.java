package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.MultiPlayerGameModeExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative.CreativeModeInventoryScreenAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.SimpleContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
	@ModifyReceiver(method = "handleLogin", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;createPlayer(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/stats/StatsCounter;Lnet/minecraft/client/ClientRecipeBook;)Lnet/minecraft/client/player/LocalPlayer;"))
	private MultiPlayerGameMode setInteractionManagerTenfoursized(MultiPlayerGameMode instance, ClientLevel level, StatsCounter statsCounter, ClientRecipeBook recipeBook) {
		boolean tenfoursized = EnnyPackets.tenfoursized != null ? EnnyPackets.tenfoursized : false;
		EnnyPackets.tenfoursized = null;

		((MultiPlayerGameModeExtensions) instance).ebi$setTenfoursized(tenfoursized);
		HackjobKitImpl.TenfoursizedProperty.setInstance(tenfoursized);
		// TODO - Entrypoint this (?), also get the original size maybe?
		CreativeModeInventoryScreenAccessor.setContainer(new SimpleContainer(tenfoursized ? 50 : 45));

		return instance;
	}
}
