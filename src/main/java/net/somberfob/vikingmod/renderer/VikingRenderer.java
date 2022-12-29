package net.somberfob.vikingmod.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.somberfob.vikingmod.VikingMod;
import net.somberfob.vikingmod.entities.Viking;
import net.somberfob.vikingmod.renderer.models.VikingModel;
import org.jetbrains.annotations.NotNull;

public class VikingRenderer extends MobRenderer<Viking, VikingModel<Viking>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(VikingMod.MOD_ID, "textures/entities/viking.png");

    public VikingRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new VikingModel(ctx.bakeLayer(VikingModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Viking entity) {
        return TEXTURE;
    }
}
