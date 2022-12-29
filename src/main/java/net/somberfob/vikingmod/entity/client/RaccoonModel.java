package net.somberfob.vikingmod.entity.client;

import net.somberfob.vikingmod.VikingMod;
import net.somberfob.vikingmod.entity.custom.RaccoonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RaccoonModel extends AnimatedGeoModel<RaccoonEntity> {


    @Override
    public ResourceLocation getModelResource(RaccoonEntity object) {
        return null;
    }

    @Override
    public ResourceLocation getTextureResource(RaccoonEntity object) {
        return null;
    }

    @Override
    public ResourceLocation getAnimationResource(RaccoonEntity animatable) {
        return null;
    }

    @Override
    public void setCustomAnimations(RaccoonEntity animatable, int instanceId) {
        super.setCustomAnimations(animatable, instanceId);
    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}