package rndm_access.timberworks.core;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import rndm_access.timberworks.Timberworks;
import rndm_access.timberworks.block_screen.WoodcutterScreenHandler;

public final class ModScreenHandlerTypes {
    public static final ScreenHandlerType<WoodcutterScreenHandler> WOODCUTTER;

    private static void register(String path, ScreenHandlerType<?> type) {
        Registry.register(Registries.SCREEN_HANDLER, Timberworks.makeModId(path), type);
    }

    public static void register() {
        register("woodcutter", WOODCUTTER);

        Timberworks.LOGGER.info("Registered screen handler types!");
    }

    static {
        WOODCUTTER = new ScreenHandlerType<>(WoodcutterScreenHandler::new, FeatureSet.empty());
    }
}
