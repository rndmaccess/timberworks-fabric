package rndm_access.timberworks.core;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import rndm_access.timberworks.Timberworks;

public final class ModPointOfInterestTypes {
    public static final ResourceKey<PoiType> LUMBERJACK;

    public static void register() {
        PoiHelper.register(Timberworks.makeModId("lumberjack"), 1, 1,
                ModBlocks.WOODCUTTER);

        Timberworks.LOGGER.info("Registered point of interest types.");
    }

    private static ResourceKey<PoiType> of() {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Timberworks.makeModId("lumberjack"));
    }

    static {
        LUMBERJACK = of();
    }
}