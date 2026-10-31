package archives.tater.censored.client.datagen;

import archives.tater.censored.client.render.UnseeableCubeRenderer;
import archives.tater.censored.registry.CensoredModBlocks;
import archives.tater.censored.registry.CensoredModItems;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import com.mojang.math.Transformation;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.renderer.item.EmptyModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.client.data.models.model.ItemModelUtils.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createParticleOnlyBlock(CensoredModBlocks.UNSEEABLE_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.itemModelOutput.accept(CensoredModItems.UNSEEABLE_BLOCK, createUnseeable(Identifier.withDefaultNamespace("block/block"), null));
        itemModelGenerators.itemModelOutput.accept(CensoredModItems.UNSEEABLE_SHARD, createUnseeable(Identifier.withDefaultNamespace("item/generated"), new Transformation(
                new Vector3f(0, 0, 15 / 32f),
                new Quaternionf(),
                new Vector3f(1, 1, 1 / 16f),
                new Quaternionf()
        )));
    }

    private static ItemModel.Unbaked createUnseeable(Identifier parent, @Nullable Transformation transformation) {
        return select(
                new DisplayContext(),
                specialModel(parent, Optional.ofNullable(transformation), new UnseeableCubeRenderer.Unbaked()),
                when(ItemDisplayContext.GUI, new EmptyModel.Unbaked())
        );
    }
}
