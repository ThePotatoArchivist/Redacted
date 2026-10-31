package archives.tater.censored.client.mixin;

import archives.tater.censored.client.CensoredClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.LevelRenderer;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@Inject(
			method = "resize",
			at = @At("TAIL")
	)
	private void resize(int width, int height, CallbackInfo ci) {
		CensoredClient.getPrevBuffer().resize(width, height);
	}

}