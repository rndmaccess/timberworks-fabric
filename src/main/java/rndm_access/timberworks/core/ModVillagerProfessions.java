package rndm_access.timberworks.core;

import com.google.common.collect.ImmutableSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;
import rndm_access.timberworks.Timberworks;

public final class ModVillagerProfessions {
    public static final VillagerProfession LUMBERJACK;

    private static VillagerProfession register(String id, VillagerProfession profession) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Timberworks.makeModId(id), profession);
    }

    private static VillagerProfession create(String id, RegistryKey<PointOfInterestType> workstation,
                                             @Nullable SoundEvent workSound) {
        return new VillagerProfession(Timberworks.makeModId(id).toString(), (entry) -> entry.matchesKey(workstation),
                (entry) -> entry.matchesKey(workstation), ImmutableSet.of(), ImmutableSet.of(), workSound);
    }

    public static void register() {
        register("lumberjack", LUMBERJACK);

        Timberworks.LOGGER.info("Registered villager professions");
    }

    static {
        LUMBERJACK = create("lumberjack",
                ModPointOfInterestTypes.LUMBERJACK, ModSoundEvents.UI_WOODCUTTER_TAKE_RESULT);
    }
}