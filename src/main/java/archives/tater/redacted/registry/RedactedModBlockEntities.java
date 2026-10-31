package archives.tater.redacted.registry;

import archives.tater.redacted.Redacted;
import archives.tater.redacted.block.entity.UnseeableBlockEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface RedactedModBlockEntities {
    BlockEntityType<UnseeableBlockEntity> UNSEEABLE_BLOCK = register("unseeable_block", FabricBlockEntityTypeBuilder.create(UnseeableBlockEntity::new, RedactedModBlocks.UNSEEABLE_BLOCK));

    private static BlockEntityType<UnseeableBlockEntity> register(String path, FabricBlockEntityTypeBuilder<UnseeableBlockEntity> builder) {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Redacted.id(path),
                builder.build()
        );
    }

    static void init() {

    }
}
