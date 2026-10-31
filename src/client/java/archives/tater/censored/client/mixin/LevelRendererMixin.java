package archives.tater.censored.client.mixin;

import archives.tater.censored.client.CensoredClient;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.FilterMode;
import net.minecraft.client.renderer.LevelRenderer;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
	@WrapOperation(
			method ="lambda$addMainPass$0",
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;bindDefaultUniforms(Lcom/mojang/renderpearl/api/commands/RenderPass;)V")
	)
	private void addPrevBuffer(RenderPass renderPass, Operation<Void> original) {
		original.call(renderPass);
		renderPass.setUniform(CensoredClient.PREV_SAMPLER_NAME, CensoredClient.getPrevBuffer().getColorTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
	}

	@Inject(
			method = "resize",
			at = @At("TAIL")
	)
	private void resize(int width, int height, CallbackInfo ci) {
		CensoredClient.getPrevBuffer().resize(width, height);
	}

}