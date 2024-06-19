package io.github.ennuil.ennuis_bigger_inventories.mixin.property.client;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.ennuil.ennuis_bigger_inventories.impl.HackjobKitImpl;
import io.github.ennuil.ennuis_bigger_inventories.impl.interfaces.property.ClientPlayerInteractionManagerExtensions;
import io.github.ennuil.ennuis_bigger_inventories.impl.networking.EnnyPackets;
import io.github.ennuil.ennuis_bigger_inventories.mixin.core.client.creative.CreativeInventoryScreenAccessor;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.recipe_book.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.stat.StatHandler;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
	@ModifyReceiver(method = "onGameJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;createPlayer(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/stat/StatHandler;Lnet/minecraft/client/recipe_book/ClientRecipeBook;)Lnet/minecraft/client/network/ClientPlayerEntity;"))
	private ClientPlayerInteractionManager setInteractionManagerTenfoursized(ClientPlayerInteractionManager instance, ClientWorld world, StatHandler statHandler, ClientRecipeBook recipeBook) {
		boolean tenfoursized = EnnyPackets.tenfoursized != null ? EnnyPackets.tenfoursized : false;
		EnnyPackets.tenfoursized = null;

		((ClientPlayerInteractionManagerExtensions) instance).ebi$setTenfoursized(tenfoursized);
		HackjobKitImpl.TenfoursizedProperty.setInstance(tenfoursized);
		// TODO - Entrypoint this (?), also get the original size maybe?
		CreativeInventoryScreenAccessor.setInventory(new SimpleInventory(tenfoursized ? 50 : 45));

		return instance;
	}
}
