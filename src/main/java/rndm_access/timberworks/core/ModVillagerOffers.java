package rndm_access.timberworks.core;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;

import java.util.Optional;

public final class ModVillagerOffers {

    public static void register() {
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.LUMBERJACK, 1, (factories) -> {
            factories.add(new ItemForEmeraldFactory(new TradedItem(Items.APPLE), 16, 2));
            String ADModId = "assorted-discoveries";

            if (FabricLoader.getInstance().isModLoaded(ADModId)) {
                Item spruceCone = Registries.ITEM.get(Identifier.of(ADModId, "spruce_cone"));
                factories.add(new ItemForEmeraldFactory(new TradedItem(spruceCone), 10, 2));
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
            factories.add(new SellItemFactory(new TradedItem(Blocks.OAK_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new TradedItem(Blocks.BIRCH_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new TradedItem(Blocks.JUNGLE_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new TradedItem(Blocks.ACACIA_LOG, 5),
                    new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new TradedItem(Blocks.DARK_OAK_LOG, 5),
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
                                         float priceMultiplier) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            TradedItem tradedItem = new TradedItem(Items.EMERALD, emeralds);
            return new TradeOffer(tradedItem, sellItem, maxTrades, xp, priceMultiplier);
        }
    }

    private record ItemForEmeraldFactory(TradedItem buyItem, int maxTrades, int xp) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(buyItem, new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
        }
    }

    private record SellItemFactory(TradedItem buyItem, ItemStack forSale, int maxTrades, int xp)
            implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            Optional<TradedItem> emeraldTradeItem = Optional.of(new TradedItem(Items.EMERALD));
            return new TradeOffer(buyItem, emeraldTradeItem, forSale, maxTrades, xp, 0.05F);
        }
    }

    private record MasterFactory(int emeralds, ItemStack forSale, int maxTrades, float priceMultiplier)
            implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            TradedItem tradedItem = new TradedItem(Items.EMERALD, emeralds);
            return new TradeOffer(tradedItem, forSale, maxTrades, 0, priceMultiplier);
        }
    }
}