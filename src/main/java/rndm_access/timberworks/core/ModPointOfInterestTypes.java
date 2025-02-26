package rndm_access.timberworks.core;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import rndm_access.timberworks.Timberworks;

public final class ModPointOfInterestTypes {
    public static final RegistryKey<PointOfInterestType> LUMBERJACK;

    public static void register() {
        PointOfInterestHelper.register(Timberworks.makeModId("lumberjack"), 1, 1,
                ModBlocks.WOODCUTTER);

        Timberworks.LOGGER.info("Registered point of interest types.");
    }

    private static RegistryKey<PointOfInterestType> of(String path) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Timberworks.makeModId(path));
    }

    static {
        LUMBERJACK = of("lumberjack");
    }
}