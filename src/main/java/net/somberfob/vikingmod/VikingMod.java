package net.somberfob.vikingmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.somberfob.vikingmod.block.ModBlocks;
import net.somberfob.vikingmod.block.entity.ModBlockEntities;
import net.somberfob.vikingmod.item.ModItems;
import net.somberfob.vikingmod.painting.ModPaintings;
import net.somberfob.vikingmod.screen.CrateScreen;
import net.somberfob.vikingmod.screen.ModMenuTypes;
import net.somberfob.vikingmod.util.ModItemProperties;
import net.somberfob.vikingmod.world.feature.ModConfiguredFeatures;
import net.somberfob.vikingmod.world.feature.ModPlacedFeatures;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VikingMod.MOD_ID)
public class VikingMod {
    public static final String MOD_ID = "vikingmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public VikingMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);


        ModPaintings.register(modEventBus);

        modEventBus.addListener(this::commonSetup);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        ModItemProperties.addCustomItemProperties();
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            MenuScreens.register(ModMenuTypes.CRATE_MENU.get(), CrateScreen::new);
        }
    }
}
