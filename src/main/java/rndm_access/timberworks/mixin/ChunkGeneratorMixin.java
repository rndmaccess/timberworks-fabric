package rndm_access.timberworks.mixin;

import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Inject(method = "trySetStructureStart", at = @At("HEAD"), cancellable = true)
    public void trySetStructureStart(StructureSet.StructureSelectionEntry weightedEntry, StructureManager structureAccessor,
                                     RegistryAccess dynamicRegistryManager, RandomState noiseConfig,
                                     StructureTemplateManager structureManager, long seed, ChunkAccess chunk, ChunkPos pos,
                                     SectionPos sectionPos, ResourceKey<Level> dimension,
                                     CallbackInfoReturnable<Boolean> cir) {
        Optional<ResourceKey<Structure>> structureKey = weightedEntry.structure().unwrapKey();

        if (structureKey.isPresent()) {
            String structureName = structureKey.get().location().toString();

            // TODO: Implement conditional structures later! Maybe do it through data instead!
            /*
            if (isStructureDisabled(structureName)) {
                cir.setReturnValue(false);
            }
            */
        }
    }

    @Inject(method = "locateStructure*", at = @At("HEAD"), cancellable = true)
    public void locateStructure(ServerLevel world, HolderSet<Structure> structures, BlockPos center,
                                int radius, boolean skipReferencedStructures,
                                CallbackInfoReturnable<Pair<BlockPos, Holder<Structure>>> cir) {
        structures.stream().forEach(structure -> {
            Optional<ResourceKey<Structure>> structureKey = structure.unwrapKey();

            if (structureKey.isPresent()) {
                String structureName = structureKey.get().location().toString();

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