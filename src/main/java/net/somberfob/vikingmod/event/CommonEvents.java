package net.somberfob.vikingmod.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.somberfob.vikingmod.VikingMod;
import net.somberfob.vikingmod.entities.ModEntities;
import net.somberfob.vikingmod.entities.Viking;

@Mod.EventBusSubscriber(modid = VikingMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.VIKING.get(), Viking.getVikingAttributes().build());
    }
}
