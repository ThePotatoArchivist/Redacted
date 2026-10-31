package archives.tater.censored.client.render;

import archives.tater.censored.client.CensoredClient;
import archives.tater.censored.client.CensoredModelLayers;
import archives.tater.censored.entity.UnseeableMob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class UnseeableMobRenderer extends HumanoidMobRenderer<UnseeableMob, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    public UnseeableMobRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(CensoredModelLayers.UNSEEABLE), _ -> CensoredClient.UNSEEABLE_RENDER_TYPE), 0);
        addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(CensoredModelLayers.UNSEEABLE_ARMOR, context.getModelSet(), HumanoidModel::new), context.getEquipmentRenderer()));
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return CensoredClient.MISSINGNO;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
