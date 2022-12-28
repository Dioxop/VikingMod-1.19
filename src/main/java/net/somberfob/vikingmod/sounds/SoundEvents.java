package net.somberfob.vikingmod.sounds;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;


public class SoundEvents {
    public static final SoundEvent VIKING_CHARGE = register("viking_charge");


    private static SoundEvent register(String p_12657_) {
        return Registry.register(Registry.SOUND_EVENT, p_12657_, new SoundEvent(new ResourceLocation(p_12657_)));
    }

}

