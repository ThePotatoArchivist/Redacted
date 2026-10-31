package archives.tater.censored.registry;

import archives.tater.censored.block.UnseeableBlock;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public interface CensoredModBlocks {
    Block UNSEEABLE_BLOCK = register(
            CensoredModBlockItemIds.UNSEEABLE_BLOCK, UnseeableBlock::new, BlockBehaviour.Properties.of()
            .noTerrainParticles()
            .noOcclusion()
    );

    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
        return register(id.block(), block, properties);
    }

    private static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
        return Registry.register(BuiltInRegistries.BLOCK, key, block.apply(properties.setId(key)));
    }

    static void init() {

    }
}
