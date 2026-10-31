package archives.tater.censored.registry;

import archives.tater.censored.Censored;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import net.minecraft.util.Unit;

public interface CensoredModAttachments {
    AttachmentType<Unit> UNSEEABLE = AttachmentRegistry.create(
            Censored.id("unseeable"), builder -> builder
			.persistent(Unit.CODEC)
			.syncWith(Unit.STREAM_CODEC, AttachmentSyncPredicate.all())
	);

	static void init() {

	}
}
