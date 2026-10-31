package archives.tater.censored.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public interface CensoredModItems {
    Item UNSEEABLE_BLOCK = register(CensoredModBlockItemIds.UNSEEABLE_BLOCK, CensoredModBlocks.UNSEEABLE_BLOCK);
    Item UNSEEABLE_SHARD = register(CensoredModItemIds.UNSEEABLE_SHARD, Item::new, new Item.Properties());

    private static Item register(BlockItemId id, Block block) {
        return register(id.item(), properties -> new BlockItem(block, properties), new Item.Properties().useBlockDescriptionPrefix());
    }

    private static Item register(BlockItemId id, Function<Item.Properties, Item> block, Item.Properties properties) {
        return register(id.item(), block, properties);
    }

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> block, Item.Properties properties) {
        return Registry.register(BuiltInRegistries.ITEM, key, block.apply(properties.setId(key)));
    }

    static void init() {

    }
}
