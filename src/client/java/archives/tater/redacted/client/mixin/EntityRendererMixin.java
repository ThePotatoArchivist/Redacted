package archives.tater.redacted.client.mixin;

import archives.tater.redacted.client.RedactedClient;
import archives.tater.redacted.registry.RedactedModAttachments;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.objectweb.asm.Opcodes;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @WrapOperation(
            method = "extractShadow",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;isInvisible:Z", opcode = Opcodes.GETFIELD)
    )
    private boolean noShadow(EntityRenderState instance, Operation<Boolean> original) {
        return original.call(instance) || instance.getDataOrDefault(RedactedClient.UNSEEABLE, false);
    }

    @SuppressWarnings("unchecked")
    @Inject(
            method = "createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void skipFrame(T entity, float partialTicks, CallbackInfoReturnable<S> cir) {
        int lagAmount = entity.getAttachedOrElse(RedactedModAttachments.LAG, 0);
        if (lagAmount <= 1) return;
        var lagState = entity.getAttached(RedactedClient.LAG_STATE);
        if (lagState != null && entity.tickCount % lagAmount != 0)
            cir.setReturnValue((S) lagState);
    }

    @ModifyReturnValue(
            method = "createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At("RETURN")
    )
    private S saveFrame(S original, T entity) {
        if (entity.getAttachedOrElse(RedactedModAttachments.LAG, 0) > 1)
            entity.setAttached(RedactedClient.LAG_STATE, original);
        else
            entity.removeAttached(RedactedClient.LAG_STATE);
        return original;
    }

    @ModifyArg(
            method = "createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;extractRenderState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V")
    )
    private float noInterpolate(float partialTicks, @Local(argsOnly = true, name = "entity")  T entity) {
        return entity.hasAttached(RedactedModAttachments.LAG) ? 0 : partialTicks;
    }

}
