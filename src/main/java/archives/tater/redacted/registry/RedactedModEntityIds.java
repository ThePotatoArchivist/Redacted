package archives.tater.redacted.registry;

import archives.tater.redacted.Redacted;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public interface RedactedModEntityIds {
    ResourceKey<EntityType<?>> UNSEEABLE = create("unseeable");
    ResourceKey<EntityType<?>> CENSORED = create("censored");

    private static ResourceKey<EntityType<?>> create(String path) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Redacted.id(path));
    }
}
