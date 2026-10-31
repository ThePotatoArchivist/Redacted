package archives.tater.censored.client;

import archives.tater.censored.Censored;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;

public interface CensoredModelLayers {
    ModelLayerLocation UNSEEABLE = createMain("unseeable");

    ArmorModelSet<ModelLayerLocation> UNSEEABLE_ARMOR = createArmorSet("unseeable");

    private static ModelLayerLocation create(String path, String layer) {
        return new ModelLayerLocation(Censored.id(path), layer);
    }

    private static ModelLayerLocation createMain(String path) {
        return create(path, "main");
    }

    private static ArmorModelSet<ModelLayerLocation> createArmorSet(String path) {
        return new ArmorModelSet<>(
                create(path, "helmet"),
                create(path, "chestplate"),
                create(path, "leggings"),
                create(path, "boots")
        );
    }

    static void init() {
        ModelLayerRegistry.registerModelLayer(UNSEEABLE, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0f), 64, 64));
        ModelLayerRegistry.registerArmorModelLayers(UNSEEABLE_ARMOR, () -> HumanoidModel.createArmorMeshSet(new CubeDeformation(0.5f), new CubeDeformation(1)).map(mesh -> LayerDefinition.create(mesh, 64, 32)));
    }
}
