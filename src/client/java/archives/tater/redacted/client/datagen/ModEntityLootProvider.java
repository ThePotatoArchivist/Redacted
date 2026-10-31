package archives.tater.redacted.client.datagen;

import archives.tater.redacted.registry.RedactedModEntities;
import archives.tater.redacted.registry.RedactedModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;

import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.level.storage.loot.LootPool.lootPool;
import static net.minecraft.world.level.storage.loot.LootTable.lootTable;
import static net.minecraft.world.level.storage.loot.entries.LootItem.lootTableItem;
import static net.minecraft.world.level.storage.loot.functions.SetItemCountFunction.setCount;
import static net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders.between;

public class ModEntityLootProvider extends FabricEntityLootSubProvider {
    public ModEntityLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        add(
                RedactedModEntities.UNSEEABLE, lootTable()
                .withPool(lootPool()
                        .add(lootTableItem(RedactedModItems.UNSEEABLE_SHARD)
                                .apply(setCount(between(0, 3)))
                        )
                )
        );
    }
}
