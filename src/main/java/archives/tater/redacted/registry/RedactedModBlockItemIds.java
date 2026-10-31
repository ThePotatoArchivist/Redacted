package archives.tater.redacted.registry;

import archives.tater.redacted.Redacted;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public interface RedactedModBlockItemIds {
    BlockItemId UNSEEABLE_BLOCK = create("unseeable_block");

    private static BlockItemId create(String path) {
        return create(Redacted.id(path));
    }

    private static BlockItemId create(Identifier id) {
        return BlockItemId.create(id, id);
    }
}
