package archives.tater.censored.client;

import archives.tater.censored.Censored;
import archives.tater.censored.client.mixin.LevelRendererAccessor;
import archives.tater.censored.client.render.UnseeableBlockRenderer;
import archives.tater.censored.client.render.UnseeableMobRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class CensoredClient implements ClientModInitializer {

    public static final RenderStateDataKey<Boolean> UNSEEABLE = RenderStateDataKey.create();
    public static final ModelLayerLocation UNSEEABLE_MOB_LAYER = new ModelLayerLocation(Censored.id("unseeable"), "main");

    public static @Nullable RenderTarget prevBuffer;

    public static final String PREV_SAMPLER_NAME = Censored.MOD_ID + "_PrevSampler";
    public static final BindGroupLayout UNSEEABLE_BIND_GROUP_LAYOUT = BindGroupLayout.builder().withUniform(PREV_SAMPLER_NAME, UniformType.COMBINED_IMAGE_SAMPLER).build();

    private static final RenderPipeline UNSEEABLE_RENDER_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.GLOBALS_SNIPPET)
            .withBindGroupLayout(BindGroupLayouts.PROJECTION)
            .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
            .withBindGroupLayout(BindGroupLayouts.FOG)
            .withVertexShader(Censored.id("core/rendertype_unseeable"))
            .withFragmentShader(Censored.id("core/rendertype_unseeable"))
            .withBindGroupLayout(UNSEEABLE_BIND_GROUP_LAYOUT)
            .withVertexBinding(0,DefaultVertexFormat.POSITION)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation(Censored.id("pipeline/unseeable"))
            .build()
    );

    public static final RenderType UNSEEABLE_RENDER_TYPE = RenderType.create(Censored.MOD_ID + "_unseeable", RenderSetup.builder(UNSEEABLE_RENDER_PIPELINE)
            .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
            .createRenderSetup());

    public static RenderTarget getPrevBuffer() {
        if (prevBuffer == null)
            prevBuffer = new TextureTarget(
                    Censored.MOD_ID + "_sky",
                    Minecraft.getInstance().getWindow().getWidth(),
                    Minecraft.getInstance().getWindow().getHeight(),
                    GpuFormat.RGBA8_UNORM,
                    null
            );
        return prevBuffer;
    }

    public static boolean isHiddenOutline(BlockState instance) {
        return instance.is(Censored.UNSEEABLE_BLOCK);
    }

    @Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BlockEntityRenderers.register(Censored.UNSEEABLE_BLOCK_ENTITY, _ -> new UnseeableBlockRenderer());
        ModelLayerRegistry.registerModelLayer(UNSEEABLE_MOB_LAYER, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0f), 64, 64));
        EntityRenderers.register(Censored.UNSEEABLE_ENTITY.value(), UnseeableMobRenderer::new);

        LevelRenderEvents.END_MAIN.register(context -> {
            getPrevBuffer().copyColorFrom(((LevelRendererAccessor) context.levelRenderer()).getTargets().main.get());
        });
	}
}