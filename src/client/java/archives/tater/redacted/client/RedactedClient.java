package archives.tater.redacted.client;

import archives.tater.redacted.Redacted;
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

import org.jetbrains.annotations.Nullable;

public class RedactedClient implements ClientModInitializer {

    public static final RenderStateDataKey<Boolean> UNSEEABLE = RenderStateDataKey.create();
    public static final AttachmentType<EntityRenderState> LAG_STATE = AttachmentRegistry.create(Redacted.id("lag_state"));

    public static final Identifier MISSINGNO = Identifier.withDefaultNamespace("missingno");

    public static @Nullable RenderTarget prevBuffer;

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
                    Redacted.MOD_ID + "_sky",
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

    @Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BlockEntityRenderers.register(RedactedModBlockEntities.UNSEEABLE_BLOCK, _ -> new UnseeableBlockRenderer());
        RedactedModelLayers.init();
        EntityRenderers.register(RedactedModEntities.UNSEEABLE, UnseeableMobRenderer::new);
        SpecialModelRenderers.ID_MAPPER.put(Redacted.id("unseeable_cube"), UnseeableCubeRenderer.Unbaked.CODEC);

	}
}