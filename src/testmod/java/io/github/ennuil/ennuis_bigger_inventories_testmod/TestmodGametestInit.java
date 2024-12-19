package io.github.ennuil.ennuis_bigger_inventories_testmod;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

public class TestmodGametestInit implements FabricGameTest {
	private static final String EMPTY_1X1_STRUCTURE = "ennuis_bigger_inventories_testmod:empty_1x1";

	@GameTest(template = EMPTY_1X1_STRUCTURE)
	public void chestIsTenfoursizedTest(GameTestHelper helper) {
		helper.setBlock(BlockPos.ZERO, Blocks.CHEST);
		helper.assertBlockEntityData(
			BlockPos.ZERO,
			blockEntity -> blockEntity instanceof ChestBlockEntity,
			() -> "Block entity doesn't match?"
		);
		helper.assertBlockEntityData(
			BlockPos.ZERO,
			blockEntity -> ((ChestBlockEntity) blockEntity).getContainerSize() == 30,
			() -> "Chest doesn't have 30 slots!"
		);
		helper.useBlock(BlockPos.ZERO);

		helper.succeed();
	}

	@GameTest(template = EMPTY_1X1_STRUCTURE)
	public void barrelIsTenfoursizedTest(GameTestHelper helper) {
		helper.setBlock(BlockPos.ZERO, Blocks.BARREL);
		helper.assertBlockEntityData(
			BlockPos.ZERO,
			blockEntity -> blockEntity instanceof BarrelBlockEntity,
			() -> "Block entity doesn't match?"
		);
		helper.assertBlockEntityData(
			BlockPos.ZERO,
			blockEntity -> ((BarrelBlockEntity) blockEntity).getContainerSize() == 30,
			() -> "Barrel doesn't have 30 slots!"
		);
		helper.useBlock(BlockPos.ZERO);

		helper.succeed();
	}

	@GameTest(template = EMPTY_1X1_STRUCTURE)
	public void shulkerBoxIsTenfoursizedTest(GameTestHelper helper) {
		helper.setBlock(BlockPos.ZERO, Blocks.LIGHT_BLUE_SHULKER_BOX);
		helper.assertBlockEntityData(
			BlockPos.ZERO,
			blockEntity -> blockEntity instanceof ShulkerBoxBlockEntity,
			() -> "Block entity doesn't match?"
		);
		helper.assertBlockEntityData(
			BlockPos.ZERO,
			blockEntity -> ((ShulkerBoxBlockEntity) blockEntity).getContainerSize() == 30,
			() -> "Shulker Box doesn't have 30 slots!"
		);
		helper.useBlock(BlockPos.ZERO);

		helper.succeed();
	}
}
