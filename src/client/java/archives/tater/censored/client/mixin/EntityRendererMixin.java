package archives.tater.censored.client.mixin;

import archives.tater.censored.client.CensoredClient;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import org.objectweb.asm.Opcodes;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @WrapOperation(
            method = "extractShadow",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;isInvisible:Z", opcode = Opcodes.GETFIELD)
    )
    private boolean noShadow(EntityRenderState instance, Operation<Boolean> original) {
        return original.call(instance) || instance.getDataOrDefault(CensoredClient.UNSEEABLE, false);
    }
}
