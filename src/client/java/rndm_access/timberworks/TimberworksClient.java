package rndm_access.timberworks;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import rndm_access.timberworks.core.ModMenus;
import rndm_access.timberworks.screen.WoodcutterScreen;

public class TimberworksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        registerScreens();
	}

    private static void registerScreens() {
        MenuScreens.register(ModMenus.WOODCUTTER, WoodcutterScreen::new);
    }
}