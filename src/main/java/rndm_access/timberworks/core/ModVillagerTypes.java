package rndm_access.timberworks.core;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import rndm_access.timberworks.Timberworks;

public final class ModVillagerTypes {
    public static final ResourceKey<VillagerType> CRIMSON = createKey("crimson");
    public static final ResourceKey<VillagerType> WARPED = createKey("warped");
    public static final ResourceKey<VillagerType> FOREST = createKey("forest");

    private static void register(final ResourceKey<VillagerType> key, ResourceKey<Biome> biome) {
        register(key, ImmutableList.of(biome));
    }

    private static void register(final ResourceKey<VillagerType> key, ImmutableList<ResourceKey<Biome>> biomes) {
        createVillagerType(key);

        // Add the villager type to each biome listed.
        for(ResourceKey<Biome> biome : biomes) {
            VillagerType.BY_BIOME.put(biome, key);
        }
    }

    private static ResourceKey<VillagerType> createKey(final String name) {
        return ResourceKey.create(Registries.VILLAGER_TYPE, Timberworks.makeModId(name));
    }

    private static void createVillagerType(final ResourceKey<VillagerType> name) {
        Registry.register(BuiltInRegistries.VILLAGER_TYPE, name, new VillagerType());
    }

    public static void register() {
        register(CRIMSON, Biomes.CRIMSON_FOREST);
        register(WARPED, Biomes.WARPED_FOREST);
        register(FOREST, ImmutableList.of(Biomes.FOREST, Biomes.FLOWER_FOREST,
                Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.DARK_FOREST));

        Timberworks.LOGGER.info("Registered villager types.");
    }
}