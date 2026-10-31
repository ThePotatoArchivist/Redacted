package archives.tater.censored.client.datagen;

import archives.tater.censored.registry.CensoredModBlocks;
import archives.tater.censored.registry.CensoredModEntities;
import archives.tater.censored.registry.CensoredModItems;

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
        translationBuilder.add(CensoredModBlocks.UNSEEABLE_BLOCK, "            Block");
        translationBuilder.add(CensoredModItems.UNSEEABLE_SHARD, "            Shard");
        translationBuilder.add(CensoredModEntities.UNSEEABLE, "            ");
    }
}
