package rndm_access.timberworks.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import rndm_access.timberworks.Timberworks;

public final class ModTradeSets {
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_1 = resourceKey("lumberjack/level_1");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_2 = resourceKey("lumberjack/level_2");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_3 = resourceKey("lumberjack/level_3");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_4 = resourceKey("lumberjack/level_4");
    public static final ResourceKey<TradeSet> LUMBERJACK_LEVEL_5 = resourceKey("lumberjack/level_5");

    private static ResourceKey<TradeSet> resourceKey(final String path) {
        return ResourceKey.create(Registries.TRADE_SET, Timberworks.makeModId(path));
    }
}
