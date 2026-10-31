package archives.tater.censored;

import archives.tater.censored.block.UnseeableBlock;
import archives.tater.censored.block.entity.UnseeableBlockEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class Censored implements ModInitializer {
	public static final String MOD_ID = "censored";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static Holder.Reference<Item> registerItem(String path, Function<Item.Properties, Item> block, Item.Properties properties) {
		var key = ResourceKey.create(Registries.ITEM, id(path));
		return Registry.registerForHolder(BuiltInRegistries.ITEM, key, block.apply(properties.setId(key)));
	}

	private static Holder.Reference<Block> registerBlock(String path, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
		var key = ResourceKey.create(Registries.BLOCK, id(path));
		return Registry.registerForHolder(BuiltInRegistries.BLOCK, key, block.apply(properties.setId(key)));
	}

	public static final Holder.Reference<Block> UNSEEABLE_BLOCK = registerBlock("unseeable", UnseeableBlock::new, BlockBehaviour.Properties.of()
			.noTerrainParticles()
			.noOcclusion()
	);

	public static final BlockEntityType<UnseeableBlockEntity> UNSEEABLE_BLOCK_ENTITY = Registry.register(
			BuiltInRegistries.BLOCK_ENTITY_TYPE,
			id("unseeable"),
			FabricBlockEntityTypeBuilder.create(UnseeableBlockEntity::new, UNSEEABLE_BLOCK.value()).build()
	);

	public static final Holder.Reference<Item> UNSEEABLE_ITEM = registerItem("unseeable", properties -> new BlockItem(UNSEEABLE_BLOCK.value(), properties), new Item.Properties());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
