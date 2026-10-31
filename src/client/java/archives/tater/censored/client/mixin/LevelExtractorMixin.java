package archives.tater.censored.client.mixin;


import archives.tater.censored.client.CensoredClient;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin {
    @WrapOperation(
            method = "extractBlockOutline",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z")
    )
    private boolean outlinePlaceInAir(BlockState instance, Operation<Boolean> original, Camera camera) {
        return original.call(instance) || CensoredClient.isHiddenOutline(instance);
    }
}
