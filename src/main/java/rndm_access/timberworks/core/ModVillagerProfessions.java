package rndm_access.timberworks.core;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.jetbrains.annotations.Nullable;
import rndm_access.timberworks.Timberworks;

public final class ModVillagerProfessions {
    public static final VillagerProfession LUMBERJACK;

    private static VillagerProfession register(String id, VillagerProfession profession) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, Timberworks.makeModId(id), profession);
    }

    private static VillagerProfession create(String id, ResourceKey<PoiType> workstation,
                                             @Nullable SoundEvent workSound) {
        return new VillagerProfession(Timberworks.makeModId(id).toString(), (entry) -> entry.is(workstation),
                (entry) -> entry.is(workstation), ImmutableSet.of(), ImmutableSet.of(), workSound);
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