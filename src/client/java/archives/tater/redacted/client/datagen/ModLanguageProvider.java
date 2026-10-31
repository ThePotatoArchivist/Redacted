package archives.tater.redacted.client.datagen;

import archives.tater.redacted.registry.RedactedModBlocks;
import archives.tater.redacted.registry.RedactedModEntities;
import archives.tater.redacted.registry.RedactedModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(RedactedModBlocks.UNSEEABLE_BLOCK, "            Block");
        translationBuilder.add(RedactedModItems.UNSEEABLE_SHARD, "            Shard");
        translationBuilder.add(RedactedModEntities.UNSEEABLE, "            ");
    }
}
