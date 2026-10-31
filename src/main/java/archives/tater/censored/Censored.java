package archives.tater.censored;

import archives.tater.censored.block.UnseeableBlock;
import archives.tater.censored.block.entity.UnseeableBlockEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.function.Function;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static java.lang.Math.toIntExact;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.commands.arguments.EntityArgument.*;

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

	public static final AttachmentType<Unit> UNSEEABLE_ATTACHMENT = AttachmentRegistry.create(id("unseeable"), builder -> builder
			.persistent(Unit.CODEC)
			.syncWith(Unit.STREAM_CODEC, AttachmentSyncPredicate.all())
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> {
			dispatcher.register(literal("unseeable")
					.then(argument("entity", entity())
							.executes(context ->
									setUnseeable(getEntities(context, "entity"), true)
							)
							.then(argument("enabled", bool())
									.executes(context ->
											setUnseeable(getEntities(context, "entity"), getBool(context, "enabled"))
									)
							)
					)
			);
		});
	}

	private static int setUnseeable(Collection<? extends Entity> entities, boolean enabled) {
		var value = enabled ? Unit.INSTANCE : null;
		return toIntExact(entities.stream().filter(entity ->
                entity instanceof LivingEntity && entity.setAttached(UNSEEABLE_ATTACHMENT, value) != value
        ).count());
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
