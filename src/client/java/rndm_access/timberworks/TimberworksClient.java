package rndm_access.timberworks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import rndm_access.timberworks.core.ModBlocks;
import rndm_access.timberworks.core.ModScreenHandlerTypes;
import rndm_access.timberworks.screen.WoodcutterScreen;

public class TimberworksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
        registerScreens();
        registerRenderLayers();
	}

    private static void registerScreens() {
        HandledScreens.register(ModScreenHandlerTypes.WOODCUTTER, WoodcutterScreen::new);
    }

    private static void registerRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.WOODCUTTER);
    }
}