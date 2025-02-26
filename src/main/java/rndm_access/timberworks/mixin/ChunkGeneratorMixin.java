package rndm_access.timberworks.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Inject(method = "trySetStructureStart", at = @At("HEAD"), cancellable = true)
    public void trySetStructureStart(StructureSet.WeightedEntry weightedEntry, StructureAccessor structureAccessor,
                                     DynamicRegistryManager dynamicRegistryManager, NoiseConfig noiseConfig,
                                     StructureTemplateManager structureManager, long seed, Chunk chunk, ChunkPos pos,
                                     ChunkSectionPos sectionPos, RegistryKey<World> dimension,
                                     CallbackInfoReturnable<Boolean> cir) {
        Optional<RegistryKey<Structure>> structureKey = weightedEntry.structure().getKey();

        if (structureKey.isPresent()) {
            String structureName = structureKey.get().getValue().toString();

            // TODO: Implement conditional structures later! Maybe do it through data instead!
            /*
            if (isStructureDisabled(structureName)) {
                cir.setReturnValue(false);
            }
            */
        }
    }

    @Inject(method = "locateStructure*", at = @At("HEAD"), cancellable = true)
    public void locateStructure(ServerWorld world, RegistryEntryList<Structure> structures, BlockPos center,
                                int radius, boolean skipReferencedStructures,
                                CallbackInfoReturnable<Pair<BlockPos, RegistryEntry<Structure>>> cir) {
        structures.stream().forEach(structure -> {
            Optional<RegistryKey<Structure>> structureKey = structure.getKey();

            if (structureKey.isPresent()) {
                String structureName = structureKey.get().getValue().toString();

                // TODO: Implement conditional structures later! Maybe do it through data instead!
                /*
                if (isStructureDisabled(structureName)) {
                    cir.setReturnValue(null);
                }
                */
            }
        });
    }

    /*
    @Unique
    private boolean isStructureDisabled(String structureName) {
        JsonConfig config = ModConfig.getInternalConfig();
        config.load();
        BooleanConfigEntry configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_WOODCUTTER);
        boolean woodcuttersEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_FOREST_CABINS);
        boolean forestCabinsEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_DARK_FOREST_CABINS);
        boolean darkForestCabinsEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_BIRCH_FOREST_CABINS);
        boolean birchForestCabinsEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_TAIGA_CABINS);
        boolean taigaCabinsEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_SNOWY_TAIGA_CABINS);
        boolean snowyTaigaCabinsEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_CRIMSON_FOREST_CABINS);
        boolean crimsonForestCabinsEnabled = configEntry.getValue();
        configEntry = (BooleanConfigEntry) config.getEntry(ModConfigKeys.ENABLE_WARPED_FOREST_CABINS);
        boolean warpedForestCabinsEnabled = configEntry.getValue();
        boolean isForestCabin = structureName.equals("assorted-discoveries:cabin_forest");
        boolean isDarkForestCabin = structureName.equals("assorted-discoveries:cabin_dark_forest");
        boolean isBirchForestCabin = structureName.equals("assorted-discoveries:cabin_birch_forest");
        boolean isTaigaCabin = structureName.equals("assorted-discoveries:cabin_taiga");
        boolean isSnowyTaigaCabin = structureName.equals("assorted-discoveries:cabin_snowy_taiga");
        boolean isCrimsonCabin = structureName.equals("assorted-discoveries:nether_cabin_crimson_forest");
        boolean isWarpedCabin = structureName.equals("assorted-discoveries:nether_cabin_warped_forest");
        boolean isCabin = isForestCabin || isDarkForestCabin || isBirchForestCabin || isTaigaCabin
                || isSnowyTaigaCabin || isCrimsonCabin || isWarpedCabin;

        if (isCabin && !woodcuttersEnabled) {
            return true;
        } else if (isForestCabin && !forestCabinsEnabled) {
            return true;
        } else if (isDarkForestCabin && !darkForestCabinsEnabled) {
            return true;
        } else if (isBirchForestCabin && !birchForestCabinsEnabled) {
            return true;
        } else if (isTaigaCabin && !taigaCabinsEnabled) {
            return true;
        } else if (isSnowyTaigaCabin && !snowyTaigaCabinsEnabled) {
            return true;
        } else if (isCrimsonCabin && !crimsonForestCabinsEnabled) {
            return true;
        } else {
            return isWarpedCabin && !warpedForestCabinsEnabled;
        }
    }
    */
}