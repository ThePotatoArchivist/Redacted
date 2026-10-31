package archives.tater.redacted.client.render;

import archives.tater.redacted.client.RedactedClient;
import archives.tater.redacted.entity.GenericMob;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;

public class CensoredMobRenderer extends EntityRenderer<GenericMob, EntityRenderState> {
    public CensoredMobRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        RedactedClient.censoredObjects.add(RedactedClient.vertices(state.x, state.y, state.z, state.boundingBoxWidth, state.boundingBoxHeight));
    }
}
