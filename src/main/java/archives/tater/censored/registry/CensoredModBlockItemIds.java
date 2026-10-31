package archives.tater.censored.registry;

import archives.tater.censored.Censored;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public interface CensoredModBlockItemIds {
    BlockItemId UNSEEABLE_BLOCK = create("unseeable_block");

    private static BlockItemId create(String path) {
        return create(Censored.id(path));
    }

    private static BlockItemId create(Identifier id) {
        return BlockItemId.create(id, id);
    }
}
