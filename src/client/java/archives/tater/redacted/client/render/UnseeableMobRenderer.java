package archives.tater.redacted.client.render;

import archives.tater.redacted.client.RedactedClient;
import archives.tater.redacted.client.RedactedModelLayers;
import archives.tater.redacted.entity.GenericMob;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class UnseeableMobRenderer extends HumanoidMobRenderer<GenericMob, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    public UnseeableMobRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(RedactedModelLayers.UNSEEABLE), _ -> RedactedClient.UNSEEABLE_RENDER_TYPE), 0);
        addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(RedactedModelLayers.UNSEEABLE_ARMOR, context.getModelSet(), HumanoidModel::new), context.getEquipmentRenderer()));
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return RedactedClient.MISSINGNO;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
