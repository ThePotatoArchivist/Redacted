package archives.tater.redacted.client;

import archives.tater.redacted.Redacted;
import archives.tater.redacted.client.render.CensoredMobRenderer;
import archives.tater.redacted.client.render.UnseeableBlockRenderer;
import archives.tater.redacted.client.render.UnseeableCubeRenderer;
import archives.tater.redacted.client.render.UnseeableMobRenderer;
import archives.tater.redacted.registry.RedactedModBlockEntities;
import archives.tater.redacted.registry.RedactedModBlocks;
import archives.tater.redacted.registry.RedactedModEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class RedactedClient implements ClientModInitializer {

    public static final RenderStateDataKey<Boolean> UNSEEABLE = RenderStateDataKey.create();
    public static final RenderStateDataKey<Boolean> CENSORED = RenderStateDataKey.create();
    public static final AttachmentType<EntityRenderState> LAG_STATE = AttachmentRegistry.create(Redacted.id("lag_state"));

    public static final Identifier MISSINGNO = Identifier.withDefaultNamespace("missingno");

    public static @Nullable RenderTarget prevBuffer;

    public static final AtomicList<Set<Vec3>> censoredObjects = new AtomicList<>();

    public static final String PREV_SAMPLER_NAME = Redacted.MOD_ID + "_PrevSampler";
    public static final BindGroupLayout UNSEEABLE_BIND_GROUP_LAYOUT = BindGroupLayout.builder().withUniform(PREV_SAMPLER_NAME, UniformType.COMBINED_IMAGE_SAMPLER).build();

    private static final RenderPipeline UNSEEABLE_RENDER_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.GLOBALS_SNIPPET)
            .withBindGroupLayout(BindGroupLayouts.PROJECTION)
            .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
            .withBindGroupLayout(BindGroupLayouts.FOG)
            .withVertexShader(Redacted.id("core/rendertype_unseeable"))
            .withFragmentShader(Redacted.id("core/rendertype_unseeable"))
            .withBindGroupLayout(UNSEEABLE_BIND_GROUP_LAYOUT)
            .withVertexBinding(0,DefaultVertexFormat.POSITION)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation(Redacted.id("pipeline/unseeable"))
            .build()
    );

    public static final RenderType UNSEEABLE_RENDER_TYPE = RenderType.create(Redacted.MOD_ID + "_unseeable", RenderSetup.builder(UNSEEABLE_RENDER_PIPELINE)
            .useOverlay()
            .affectsCrumbling()
            .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
            .withTexture("Sampler0", MISSINGNO) // Necessary to affect outline
            .createRenderSetup());

    public static RenderTarget getPrevBuffer() {
        if (prevBuffer == null)
            prevBuffer = new TextureTarget(
                    Redacted.MOD_ID + "_prev",
                    Minecraft.getInstance().getWindow().getWidth(),
                    Minecraft.getInstance().getWindow().getHeight(),
                    GpuFormat.RGBA8_UNORM,
                    null
            );
        return prevBuffer;
    }

    public static boolean isHiddenOutline(BlockState instance) {
        return instance.is(RedactedModBlocks.UNSEEABLE_BLOCK);
    }

    public static Set<Vec3> vertices(double x, double y, double z, double width, double height) {
        var radius = width / 2;
        var minX = x - radius;
        var maxX = x + radius;
        var minZ = z - radius;
        var maxZ = z + radius;
        var maxY = y + height;
        return Set.of(
                new Vec3(minX, y, minZ),
                new Vec3(minX, y, maxZ),
                new Vec3(maxX, y, minZ),
                new Vec3(maxX, y, maxZ),
                new Vec3(minX, maxY, minZ),
                new Vec3(minX, maxY, maxZ),
                new Vec3(maxX, maxY, minZ),
                new Vec3(maxX, maxY, maxZ)
        );
    }

    @Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BlockEntityRenderers.register(RedactedModBlockEntities.UNSEEABLE_BLOCK, _ -> new UnseeableBlockRenderer());
        RedactedModelLayers.init();
        EntityRenderers.register(RedactedModEntities.UNSEEABLE, UnseeableMobRenderer::new);
        EntityRenderers.register(RedactedModEntities.CENSORED, CensoredMobRenderer::new);
        SpecialModelRenderers.ID_MAPPER.put(Redacted.id("unseeable_cube"), UnseeableCubeRenderer.Unbaked.CODEC);

        LevelRenderEvents.END_MAIN.register(_ -> {
            censoredObjects.commit();
        });

        HudElementRegistry.addLast(Redacted.id("censored"), (graphics, deltaTracker) -> {
            var gameRenderer = Minecraft.getInstance().gameRenderer;
            var guiWidth = graphics.guiWidth();
            var guiHeight = graphics.guiHeight();
            for (var vertices : censoredObjects) {
                var minX = guiWidth;
                var minY = guiHeight;
                var maxX = 0;
                var maxY = 0;
                var minZ = 0.0;
                for (var vertex : vertices) {
                    var point = gameRenderer.projectPointToScreen(vertex);
                    var x = (int) (guiWidth * (point.x + 1) / 2);
                    var y = (int) (guiHeight * (1 - point.y) / 2);
                    minX = min(minX, x);
                    maxX = max(maxX, x);
                    minY = min(minY, y);
                    maxY = max(maxY, y);
                    minZ = min(minZ, point.z);
                }
                if (minZ > 0) continue;
                graphics.fill(minX, minY, maxX, maxY, 0xFF000000);
            }
        });
	}
}