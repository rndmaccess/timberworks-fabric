package rndm_access.timberworks.core;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import rndm_access.timberworks.Timberworks;

public final class ModVillagerTypes {

    private static void register(String path, ResourceKey<Biome> biome) {
        register(path, ImmutableList.of(biome));
    }

    private static void register(String path, ImmutableList<ResourceKey<Biome>> biomes) {
        VillagerType type = createVillagerType(Timberworks.makeModId(path));

        // Add the villager type to each biome listed.
        for(ResourceKey<Biome> biome : biomes) {
            VillagerType.BY_BIOME.put(biome, type);
        }
    }

    private static VillagerType createVillagerType(ResourceLocation id) {
        return Registry.register(BuiltInRegistries.VILLAGER_TYPE, ResourceLocation.parse(id.toString()),
                new VillagerType(id.toString()));
    }

    public static void register() {
        register("crimson", Biomes.CRIMSON_FOREST);
        register("warped", Biomes.WARPED_FOREST);
        register("forest", ImmutableList.of(Biomes.FOREST, Biomes.FLOWER_FOREST,
                Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.DARK_FOREST));

        Timberworks.LOGGER.info("Registered villager types.");
    }
}