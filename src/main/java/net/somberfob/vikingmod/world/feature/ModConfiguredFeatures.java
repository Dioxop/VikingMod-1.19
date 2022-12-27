package net.somberfob.vikingmod.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.somberfob.vikingmod.VikingMod;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.somberfob.vikingmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, VikingMod.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SILVER_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> SILVER_ORE = CONFIGURED_FEATURES.register("silver_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_SILVER_ORES.get(),7)));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SLATE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SLATE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_SLATE_ORE.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> SLATE_ORE = CONFIGURED_FEATURES.register("slate_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_SLATE_ORES.get(),10)));


    public static final RegistryObject<ConfiguredFeature<?, ?>> MAHOGANY =
            CONFIGURED_FEATURES.register("mahogany", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ModBlocks.MAHOGANY_LOG.get()),
                            new StraightTrunkPlacer(5, 6, 3),
                            BlockStateProvider.simple(ModBlocks.MAHOGANY_LEAVES.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                            new TwoLayersFeatureSize(1, 0, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MAHOGANY_SPAWN =
            CONFIGURED_FEATURES.register("red_maple_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            ModPlacedFeatures.MAHOGANY_CHECKED.getHolder().get(),
                            0.5F)), ModPlacedFeatures.MAHOGANY_CHECKED.getHolder().get())));





    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}