package rndm_access.timberworks.core;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import rndm_access.timberworks.Timberworks;

public final class ModSoundEvents {
    public static final SoundEvent UI_WOODCUTTER_TAKE_RESULT = register("ui.woodcutter.take_result");

    public static void register() {
        Timberworks.LOGGER.info("Registered sound events!");
    }

    private static SoundEvent register(String name) {
        SoundEvent soundEvent = SoundEvent.of(Timberworks.makeModId(name));
        return Registry.register(Registries.SOUND_EVENT, soundEvent.id(), soundEvent);
    }
}
