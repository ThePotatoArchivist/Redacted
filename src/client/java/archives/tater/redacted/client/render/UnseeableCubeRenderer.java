package archives.tater.redacted.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;

import org.joml.Vector3fc;

import java.util.function.Consumer;

public class UnseeableCubeRenderer implements NoDataSpecialModelRenderer {
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        UnseeableBlockRenderer.submitSpecial(poseStack, submitNodeCollector, outlineColor);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        UnseeableBlockRenderer.getExtents(output);
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(new Unbaked());

        @Override
        public SpecialModelRenderer<Void> bake(BakingContext context) {
            return new UnseeableCubeRenderer();
        }

        @Override
        public MapCodec<? extends NoDataSpecialModelRenderer.Unbaked> type() {
            return CODEC;
        }
    }
}
