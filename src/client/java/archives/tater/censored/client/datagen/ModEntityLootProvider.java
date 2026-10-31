package archives.tater.censored.client.datagen;

import archives.tater.censored.registry.CensoredModEntities;
import archives.tater.censored.registry.CensoredModItems;

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
                CensoredModEntities.UNSEEABLE, lootTable()
                .withPool(lootPool()
                        .add(lootTableItem(CensoredModItems.UNSEEABLE_SHARD)
                                .apply(setCount(between(0, 3)))
                        )
                )
        );
    }
}
