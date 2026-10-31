package archives.tater.redacted.registry;

import archives.tater.redacted.entity.GenericMob;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public interface RedactedModEntities {
    EntityType<GenericMob> UNSEEABLE = register(RedactedModEntityIds.UNSEEABLE, EntityType.Builder.of(GenericMob::new, MobCategory.MONSTER)
            .sized(0.6f, 1.8f)
            .eyeHeight(1.62f)
            .vehicleAttachment(Avatar.DEFAULT_VEHICLE_ATTACHMENT)
            .clientTrackingRange(8)
            .notInPeaceful()
    );

    EntityType<GenericMob> CENSORED = register(RedactedModEntityIds.CENSORED, EntityType.Builder.of(GenericMob::new, MobCategory.MONSTER)
            .sized(0.6f, 1.8f)
            .eyeHeight(1.62f)
            .vehicleAttachment(Avatar.DEFAULT_VEHICLE_ATTACHMENT)
            .clientTrackingRange(8)
            .notInPeaceful()
    );

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
		return Registry.register(
				BuiltInRegistries.ENTITY_TYPE,
				key,
				builder.build(key)
		);
	}

    static void init() {
        FabricDefaultAttributeRegistry.register(RedactedModEntities.UNSEEABLE, GenericMob.createAttributes());
        FabricDefaultAttributeRegistry.register(RedactedModEntities.CENSORED, GenericMob.createAttributes());
    }
}
