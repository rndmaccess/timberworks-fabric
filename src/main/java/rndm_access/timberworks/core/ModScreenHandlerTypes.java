package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.block_screen.WoodcutterScreenHandler;

public final class ModScreenHandlerTypes {
    public static final MenuType<WoodcutterScreenHandler> WOODCUTTER;

    private static void register(String path, MenuType<?> type) {
        Registry.register(BuiltInRegistries.MENU, Timberworks.makeModId(path), type);
    }

    public static void register() {
        register("woodcutter", WOODCUTTER);

        Timberworks.LOGGER.info("Registered screen handler types!");
    }

    static {
        WOODCUTTER = new MenuType<>(WoodcutterScreenHandler::new, FeatureFlagSet.of());
    }
}
