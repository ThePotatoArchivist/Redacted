package archives.tater.redacted.client.render;


import archives.tater.redacted.block.entity.UnseeableBlockEntity;
import archives.tater.redacted.client.RedactedClient;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.EndPortalRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static net.minecraft.util.Util.makeEnumMap;

/**
 * @see net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer
 */
public class UnseeableBlockRenderer implements BlockEntityRenderer<UnseeableBlockEntity, EndPortalRenderState> {
    private static final Vector3fc FROM = new Vector3f(0.0F, 0.0F, 0.0F);
    private static final Vector3fc TO = new Vector3f(1.0F, 1.0F, 1.0F);
    private static final Map<Direction, List<Vector3fc>> FACES = makeEnumMap(Direction.class, direction -> {
        var faceInfo = FaceInfo.fromFacing(direction);
        return List.of(
                faceInfo.getVertexInfo(0).select(FROM, TO),
                faceInfo.getVertexInfo(1).select(FROM, TO),
                faceInfo.getVertexInfo(2).select(FROM, TO),
                faceInfo.getVertexInfo(3).select(FROM, TO)
        );
    });
    private static final List<Direction> ALL_FACES = List.of(Direction.values());

    @Override
    public EndPortalRenderState createRenderState() {
        return new EndPortalRenderState();
    }

    @Override
    public void extractRenderState(UnseeableBlockEntity blockEntity, EndPortalRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.facesToShow.clear();

        for (var direction : Direction.values())
            if (blockEntity.shouldRenderFace(direction))
                state.facesToShow.add(direction);
    }

    @Override
    public void submit(EndPortalRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        submitCube(state.facesToShow, poseStack, submitNodeCollector);
    }

    protected static void submitCube(final Collection<Direction> facesToShow, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector) {
        submitCube(facesToShow, poseStack, submitNodeCollector, _ -> {});
    }

    private static void submitCube(final Collection<Direction> facesToShow, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final Consumer<VertexConsumer> vertexDecorator) {
        if (facesToShow.isEmpty()) return;

        submitNodeCollector.submitCustomGeometry(poseStack, RedactedClient.UNSEEABLE_RENDER_TYPE, (pose, buffer) -> {
            for (var direction : facesToShow)
                for (Vector3fc faceVertex : FACES.get(direction))
                    vertexDecorator.accept(buffer.addVertex(pose, faceVertex));
        });
    }

    public static void submitSpecial(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int outlineColor) {
        submitCube(ALL_FACES, poseStack, submitNodeCollector);
//        if (outlineColor != 0)
//            submitCube(ALL_FACES, RenderTypes.endGateway().outline().orElseThrow(), poseStack, submitNodeCollector, (vertex) -> vertex.setUv(0.0F, 0.0F).setColor(outlineColor));
    }

    public static void getExtents(final Consumer<Vector3fc> output) {
        FACES.values().forEach((vertices) -> vertices.forEach(output));
    }
}
