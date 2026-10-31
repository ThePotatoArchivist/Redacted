package archives.tater.redacted.client.mixin;

import archives.tater.redacted.client.RedactedClient;
import archives.tater.redacted.registry.RedactedModAttachments;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
    @ModifyExpressionValue(
            method = "getRenderType",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;entityTranslucentCull(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"),
                    @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderType(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;")
            }
    )
    private RenderType unseeable(RenderType original, S state) {
        return state.getDataOrDefault(RedactedClient.UNSEEABLE, false) ? RedactedClient.UNSEEABLE_RENDER_TYPE : original;
    }

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void setUnseeable(T entity, S state, float partialTicks, CallbackInfo ci) {
        state.setData(RedactedClient.UNSEEABLE, entity.hasAttached(RedactedModAttachments.UNSEEABLE));
    }
}
