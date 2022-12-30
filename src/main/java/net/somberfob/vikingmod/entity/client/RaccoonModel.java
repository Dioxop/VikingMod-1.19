package net.somberfob.vikingmod.entity.client;

import net.somberfob.vikingmod.VikingMod;
import net.somberfob.vikingmod.entity.custom.RaccoonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RaccoonModel extends AnimatedGeoModel<RaccoonEntity> {

    @Override
    public ResourceLocation getModelResource(RaccoonEntity object) {
        return new ResourceLocation(VikingMod.MOD_ID, "geo/raccoon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RaccoonEntity object) {
        return new ResourceLocation(VikingMod.MOD_ID, "textures/entity/raccoon/raccoon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RaccoonEntity animatable) {
        return new ResourceLocation(VikingMod.MOD_ID, "animations/raccoon.animation.json");
    }
}