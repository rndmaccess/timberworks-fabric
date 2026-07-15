package rndm_access.timberworks.rei;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import rndm_access.timberworks.Timberworks;

public class ReiTimberworksCommonPlugin implements REICommonPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Timberworks.makeModId("woodcutting"), WoodcuttingDisplay.SERIALIZER);
    }
}
