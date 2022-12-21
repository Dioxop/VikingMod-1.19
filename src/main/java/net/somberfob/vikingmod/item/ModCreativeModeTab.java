package net.somberfob.vikingmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ORES_TAB = new CreativeModeTab("orestab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SILVER.get());
        }
    };
    public static final CreativeModeTab Food_Tab = new CreativeModeTab("foodtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CHICKEN_LEG.get());
        }
    };
    public static final CreativeModeTab TOOLS_TAB = new CreativeModeTab("toolstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SILVER_SWORD.get());
        }
    };
}