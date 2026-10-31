package archives.tater.censored.registry;

import archives.tater.censored.Censored;
import archives.tater.censored.block.entity.UnseeableBlockEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface CensoredModBlockEntities {
    BlockEntityType<UnseeableBlockEntity> UNSEEABLE_BLOCK = register("unseeable_block", FabricBlockEntityTypeBuilder.create(UnseeableBlockEntity::new, CensoredModBlocks.UNSEEABLE_BLOCK));

    private static BlockEntityType<UnseeableBlockEntity> register(String path, FabricBlockEntityTypeBuilder<UnseeableBlockEntity> builder) {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Censored.id(path),
                builder.build()
        );
    }

    static void init() {

    }
}
