package rndm_access.timberworks.core;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import java.util.Optional;

public final class ModVillagerOffers {

    public static void register() {
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.LUMBERJACK, 1, (factories) -> {
            factories.add(new ItemForEmeraldFactory(new ItemCost(Items.APPLE), 16, 2));
            String ADModId = "assorted-discoveries";

            if (FabricLoader.getInstance().isModLoaded(ADModId)) {
                Item spruceCone = BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(ADModId, "spruce_cone"));
                factories.add(new ItemForEmeraldFactory(new ItemCost(spruceCone), 10, 2));
            }
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.LUMBERJACK, 2, factories -> {
            factories.add(new EmeraldForItemFactory(2, new ItemStack(Items.GOLDEN_AXE), 6,
                    10, 0.02F));
            factories.add(new EmeraldForItemFactory(3, new ItemStack(Items.IRON_AXE), 3,
                    15, 0.02F));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.LUMBERJACK,
                3, factories -> {
            factories.add(new SellItemFactory(new ItemCost(Blocks.OAK_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemCost(Blocks.BIRCH_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemCost(Blocks.JUNGLE_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemCost(Blocks.ACACIA_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemCost(Blocks.DARK_OAK_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.LUMBERJACK, 4, factories -> {
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.OAK_SAPLING, 3),
                    5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.BIRCH_SAPLING, 3),
                    5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.JUNGLE_SAPLING, 5),
                    5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.ACACIA_SAPLING, 5),
                    5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.DARK_OAK_SAPLING, 10),
                    5, 20, 0.05F));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.LUMBERJACK, 5, factories -> {
            factories.add(new MasterFactory(1, new ItemStack(Blocks.NOTE_BLOCK),
                    10, 0.05F));
            factories.add(new MasterFactory(5, new ItemStack(Blocks.JUKEBOX),
                    5, 0.05F));
        });
    }

    private record EmeraldForItemFactory(int emeralds, ItemStack sellItem, int maxTrades, int xp,
                                         float priceMultiplier) implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            ItemCost tradedItem = new ItemCost(Items.EMERALD, emeralds);
            return new MerchantOffer(tradedItem, sellItem, maxTrades, xp, priceMultiplier);
        }
    }

    private record ItemForEmeraldFactory(ItemCost buyItem, int maxTrades, int xp) implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            return new MerchantOffer(buyItem, new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
        }
    }

    private record SellItemFactory(ItemCost buyItem, ItemStack forSale, int maxTrades, int xp)
            implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            Optional<ItemCost> emeraldTradeItem = Optional.of(new ItemCost(Items.EMERALD));
            return new MerchantOffer(buyItem, emeraldTradeItem, forSale, maxTrades, xp, 0.05F);
        }
    }

    private record MasterFactory(int emeralds, ItemStack forSale, int maxTrades, float priceMultiplier)
            implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            ItemCost tradedItem = new ItemCost(Items.EMERALD, emeralds);
            return new MerchantOffer(tradedItem, forSale, maxTrades, 0, priceMultiplier);
        }
    }
}