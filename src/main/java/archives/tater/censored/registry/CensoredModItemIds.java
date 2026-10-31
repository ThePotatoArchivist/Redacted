package archives.tater.censored.registry;

import archives.tater.censored.Censored;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public interface CensoredModItemIds {
    ResourceKey<Item> UNSEEABLE_SHARD = create("unseeable_shard");

    private static ResourceKey<Item> create(String path) {
        return ResourceKey.create(Registries.ITEM, Censored.id(path));
    }
}
