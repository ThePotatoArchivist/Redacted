package archives.tater.censored.registry;

import archives.tater.censored.Censored;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public interface CensoredModEntityIds {
    ResourceKey<EntityType<?>> UNSEEABLE = create("unseeable");

    private static ResourceKey<EntityType<?>> create(String path) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Censored.id(path));
    }
}
