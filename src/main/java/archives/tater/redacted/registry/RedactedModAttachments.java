package archives.tater.redacted.registry;

import archives.tater.redacted.Redacted;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;

public interface RedactedModAttachments {
    AttachmentType<Unit> UNSEEABLE = AttachmentRegistry.create(
            Redacted.id("unseeable"), builder -> builder
			.persistent(Unit.CODEC)
			.syncWith(Unit.STREAM_CODEC, AttachmentSyncPredicate.all())
	);

	AttachmentType<Integer> LAG = AttachmentRegistry.create(
			Redacted.id("lag"), builder -> builder
					.persistent(ExtraCodecs.POSITIVE_INT)
					.syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all())
	);

	static void init() {

	}
}
