package archives.tater.redacted.client.mixin;

import archives.tater.redacted.client.RedactedClient;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private RenderTarget mainRenderTarget;

    @Inject(
            method = "renderLevel",
            at = @At("TAIL")
    )
    private void copyPrevBuffer(CallbackInfo ci) {
        RedactedClient.getPrevBuffer().copyColorFrom(mainRenderTarget);
    }
}
