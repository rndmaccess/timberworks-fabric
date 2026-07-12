package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.menu.WoodcutterMenu;

public final class ModMenus {
    public static final MenuType<WoodcutterMenu> WOODCUTTER = register(new MenuType<>(WoodcutterMenu::new, FeatureFlagSet.of()));;

    @SuppressWarnings("unchecked")
    private static MenuType<WoodcutterMenu> register(MenuType<?> type) {
        return (MenuType<WoodcutterMenu>) Registry.register(BuiltInRegistries.MENU, Timberworks.makeModId("woodcutter"), type);
    }

    public static void register() {
        Timberworks.LOGGER.info("Registered screen handler types!");
    }
}
