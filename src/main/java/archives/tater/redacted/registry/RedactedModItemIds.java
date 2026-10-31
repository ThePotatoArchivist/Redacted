package archives.tater.redacted.registry;

import archives.tater.redacted.Redacted;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public interface RedactedModItemIds {
    ResourceKey<Item> UNSEEABLE_SHARD = create("unseeable_shard");

    private static ResourceKey<Item> create(String path) {
        return ResourceKey.create(Registries.ITEM, Redacted.id(path));
    }
}
