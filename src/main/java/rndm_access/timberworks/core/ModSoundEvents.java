package rndm_access.timberworks.core;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import rndm_access.timberworks.Timberworks;

public final class ModSoundEvents {
    public static final SoundEvent UI_WOODCUTTER_TAKE_RESULT = register("ui.woodcutter.take_result");

    public static void register() {
        Timberworks.LOGGER.info("Registered sound events!");
    }

    private static SoundEvent register(String name) {
        Identifier soundId = Timberworks.makeModId(name);
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(soundId);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, soundId, soundEvent);
    }
}
