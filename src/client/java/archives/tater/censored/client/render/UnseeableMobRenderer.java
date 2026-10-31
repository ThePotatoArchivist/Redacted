package archives.tater.censored.client.render;

import archives.tater.censored.client.CensoredClient;
import archives.tater.censored.entity.UnseeableMob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class UnseeableMobRenderer extends HumanoidMobRenderer<UnseeableMob, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    public static final Identifier MISSINGNO = Identifier.withDefaultNamespace("missingno");

    public UnseeableMobRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(CensoredClient.UNSEEABLE_MOB_LAYER), _ -> CensoredClient.UNSEEABLE_RENDER_TYPE), 0);
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return MISSINGNO;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
