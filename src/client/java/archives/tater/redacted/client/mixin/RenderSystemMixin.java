package archives.tater.redacted.client.mixin;

import archives.tater.redacted.client.RedactedClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.FilterMode;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    @Inject(
            method = "bindDefaultUniforms",
            at = @At("TAIL")
    )
    private static void addPrevBuffer(RenderPass renderPass, CallbackInfo ci) {
        renderPass.setUniform(RedactedClient.PREV_SAMPLER_NAME, RedactedClient.getPrevBuffer().getColorTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
    }
}
