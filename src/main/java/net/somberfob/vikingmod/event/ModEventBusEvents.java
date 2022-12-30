package net.somberfob.vikingmod.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.somberfob.vikingmod.VikingMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.somberfob.vikingmod.entity.ModEntityTypes;
import net.somberfob.vikingmod.entity.custom.RaccoonEntity;


@Mod.EventBusSubscriber(modid = VikingMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {


        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.RACCOON.get(), RaccoonEntity.setAttributes());
        };
    }