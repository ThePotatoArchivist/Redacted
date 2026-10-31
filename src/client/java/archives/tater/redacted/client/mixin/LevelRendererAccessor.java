package archives.tater.redacted.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {
    @Accessor
    LevelTargetBundle getTargets();
}
