package archives.tater.censored.client.datagen;

import archives.tater.censored.Censored;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createParticleOnlyBlock(Censored.UNSEEABLE_BLOCK.value());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

    }
}
