package rndm_access.timberworks.block_screen;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import rndm_access.timberworks.core.ModBlocks;
import rndm_access.timberworks.core.ModScreenHandlerTypes;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

import java.util.List;

public class WoodcutterScreenHandler extends AbstractContainerMenu {
    private final ContainerLevelAccess context;
    private final DataSlot selectedRecipe;
    private SelectableRecipe.SingleInputSet<WoodcuttingRecipe> availableRecipes;
    private ItemStack inputStack;
    private long lastTakeTime;
    private final Slot inputSlot;
    private final Slot outputSlot;
    private Runnable contentsChangedListener;
    public final Container input;
    private final ResultContainer output;

    public WoodcutterScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    public WoodcutterScreenHandler(int syncId, Inventory playerInventory,
                                   ContainerLevelAccess context) {
        super(ModScreenHandlerTypes.WOODCUTTER, syncId);
        this.selectedRecipe = DataSlot.standalone();
        //this.availableRecipes = Lists.newArrayList();
        this.inputStack = ItemStack.EMPTY;
        this.contentsChangedListener = () -> {};
        this.input = new SimpleContainer(1) {
            @Override
            public void setChanged() {
                super.setChanged();
                WoodcutterScreenHandler.this.slotsChanged(this);
                WoodcutterScreenHandler.this.contentsChangedListener.run();
            }
        };
        this.output = new ResultContainer();
        this.context = context;
        Level level = playerInventory.player.level();
        this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
        this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player, stack.getCount());
                WoodcutterScreenHandler.this.output.awardUsedRecipes(player, this.getInputStacks());
                ItemStack itemStack = WoodcutterScreenHandler.this.inputSlot.remove(1);

                if (!itemStack.isEmpty()) {
                    WoodcutterScreenHandler.this.populateResult();
                }

                context.execute((world, pos) -> {
                    long l = world.getGameTime();
                    if (WoodcutterScreenHandler.this.lastTakeTime != l) {
                        world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT,
                                SoundSource.BLOCKS, 1.0F, 1.0F);
                        WoodcutterScreenHandler.this.lastTakeTime = l;
                    }
                });
                super.onTake(player, stack);
            }

            private List<ItemStack> getInputStacks() {
                return List.of(WoodcutterScreenHandler.this.inputSlot.getItem());
            }
        });

        this.addPlayerInventorySlots(playerInventory);
        this.addPlayerHotbarSlots(playerInventory);
        this.addDataSlot(this.selectedRecipe);
    }

    private void addPlayerInventorySlots(Inventory playerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18,
                        84 + i * 18));
            }
        }
    }

    private void addPlayerHotbarSlots(Inventory playerInventory) {
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public SelectableRecipe.SingleInputSet<WoodcuttingRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    public boolean canCraft() {
        return this.inputSlot.hasItem() && !this.availableRecipes.isEmpty();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.context, player, ModBlocks.WOODCUTTER);
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }
        return true;
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < this.availableRecipes.size();
    }

    @Override
    public void slotsChanged(Container inventory) {
        ItemStack itemStack = this.inputSlot.getItem();
        if (!itemStack.is(this.inputStack.getItem())) {
            this.inputStack = itemStack.copy();
            this.updateInput(inventory, itemStack);
        }
    }

    //StonecutterScreenHandler

    private void updateInput(Container input, ItemStack stack) {
        this.selectedRecipe.set(-1);
        this.outputSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            //this.availableRecipes = .getStonecutterRecipes().filter(stack);
        } else {
            this.availableRecipes = SelectableRecipe.SingleInputSet.empty();
        }

        if (!stack.isEmpty()) {
            //this.availableRecipes = this.world.getRecipeManager().getStonecutterRecipes()
            //        .getAllMatches(ModRecipeTypes.WOODCUTTING, input, this.world);
        }
    }

    public void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
            //RecipeEntry<WoodcuttingRecipe> woodcuttingRecipe = this.availableRecipes.entries().get(this.selectedRecipe.get());
            //this.output.setLastRecipe(woodcuttingRecipe);
            //this.outputSlot.setStack(woodcuttingRecipe.value()
             //       .craft(this.input, this.world.getRegistryManager()));
        } else {
            this.outputSlot.setByPlayer(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    @Override
    public MenuType<?> getType() {
        return ModScreenHandlerTypes.WOODCUTTER;
    }

    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.output && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (index == 1) {
                item.onCraftedBy(itemStack2, player);
                if (!this.moveItemStackTo(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index == 0) {
                if (!this.moveItemStackTo(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            }
            /*
            else if (this.world.getRecipeManager().getFirstMatch(ModRecipeTypes.WOODCUTTING,
                    new SimpleInventory(itemStack2), this.world).isPresent()) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            */
            else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemStack2, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemStack2, 2,
                    29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
            this.broadcastChanges();
        }
        return itemStack;
    }

    @Override
    public void removed(@NonNull Player player) {
        super.removed(player);
        this.output.removeItemNoUpdate(1);
        this.context.execute((world, pos) -> this.clearContainer(player, this.input));
    }
}
