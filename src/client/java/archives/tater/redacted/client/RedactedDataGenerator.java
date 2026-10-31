package archives.tater.redacted.client;

import archives.tater.redacted.client.datagen.*;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RedactedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModLanguageProvider::new);

		pack.addProvider(ModBlockLootProvider::new);
		pack.addProvider(ModEntityLootProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
