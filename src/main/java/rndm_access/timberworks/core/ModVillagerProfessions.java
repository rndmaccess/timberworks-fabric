package rndm_access.timberworks.core;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;
import rndm_access.timberworks.Timberworks;

import java.util.function.Predicate;

public final class ModVillagerProfessions {
    public static final ResourceKey<VillagerProfession> LUMBERJACK = createKey("lumberjack");

    private static void register(final ResourceKey<VillagerProfession> name, final ResourceKey<PoiType> jobSite, final @Nullable SoundEvent workSound, final Int2ObjectMap<ResourceKey<TradeSet>> trades) {
        register(name, (poiType) -> poiType.is(jobSite), (poiType) -> poiType.is(jobSite), workSound, trades);
    }

    private static void register(final ResourceKey<VillagerProfession> name, final Predicate<Holder<PoiType>> heldJobSite, final Predicate<Holder<PoiType>> acquirableJobSite, final @Nullable SoundEvent workSound, final Int2ObjectMap<ResourceKey<TradeSet>> trades) {
        register(name, heldJobSite, acquirableJobSite, ImmutableSet.of(), ImmutableSet.of(), workSound, trades);
    }

    private static void register(final ResourceKey<VillagerProfession> name, final Predicate<Holder<PoiType>> heldJobSite, final Predicate<Holder<PoiType>> acquirableJobSite, final ImmutableSet<Item> requestedItems, final ImmutableSet<Block> secondaryPoi, final @Nullable SoundEvent workSound, final Int2ObjectMap<ResourceKey<TradeSet>> trades) {
        String namespace = name.identifier().getNamespace();
        Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, name, new VillagerProfession(Component.translatable("entity." + namespace + ".villager." + name.identifier().getPath()), heldJobSite, acquirableJobSite, requestedItems, secondaryPoi, workSound, trades));
    }

    private static ResourceKey<VillagerProfession> createKey(final String name) {
        return ResourceKey.create(Registries.VILLAGER_PROFESSION, Timberworks.makeModId(name));
    }

    public static void register() {
        register(LUMBERJACK, ModPointOfInterestTypes.LUMBERJACK,
                ModSoundEvents.UI_WOODCUTTER_TAKE_RESULT,
                Int2ObjectMap.ofEntries(
                        Int2ObjectMap.entry(1, ModTradeSets.LUMBERJACK_LEVEL_1),
                        Int2ObjectMap.entry(2, ModTradeSets.LUMBERJACK_LEVEL_2),
                        Int2ObjectMap.entry(3, ModTradeSets.LUMBERJACK_LEVEL_3),
                        Int2ObjectMap.entry(4, ModTradeSets.LUMBERJACK_LEVEL_4),
                        Int2ObjectMap.entry(5, ModTradeSets.LUMBERJACK_LEVEL_5)));

        Timberworks.LOGGER.info("Registered villager professions");
    }
}