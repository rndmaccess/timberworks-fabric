package rndm_access.timberworks.menu;

import java.util.List;
import java.util.Optional;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import rndm_access.timberworks.core.*;
import rndm_access.timberworks.recipe.WoodcuttingRecipe;

public class WoodcutterMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final DataSlot selectedRecipeIndex;
    private final Level level;
    private SelectableRecipe.SingleInputSet<WoodcuttingRecipe> recipesForInput;
    private ItemStack input;
    private long lastSoundTime;
    private final Slot inputSlot;
    private final Slot resultSlot;
    private Runnable slotUpdateListener;
    public final Container container;
    private final ResultContainer resultContainer;

    public WoodcutterMenu(final int containerId, final Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public WoodcutterMenu(final int containerId, final Inventory inventory, final ContainerLevelAccess access) {
        super(ModMenus.WOODCUTTER, containerId);
        this.selectedRecipeIndex = DataSlot.standalone();
        this.recipesForInput = SingleInputSet.empty();
        this.input = ItemStack.EMPTY;
        this.slotUpdateListener = () -> {
        };
        this.container = new SimpleContainer(1) {
            public void setChanged() {
                super.setChanged();
                WoodcutterMenu.this.slotsChanged(this);
                WoodcutterMenu.this.slotUpdateListener.run();
            }
        };
        this.resultContainer = new ResultContainer();
        this.access = access;
        this.level = inventory.player.level();
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            public boolean mayPlace(final @NonNull ItemStack itemStack) {
                return false;
            }

            public void onTake(final @NonNull Player player, final @NonNull ItemStack carried) {
                carried.onCraftedBy(player, carried.getCount());
                WoodcutterMenu.this.resultContainer.awardUsedRecipes(player, this.getRelevantItems());
                ItemStack remaining = WoodcutterMenu.this.inputSlot.remove(1);
                if (!remaining.isEmpty()) {
                    WoodcutterMenu.this.setupResultSlot(WoodcutterMenu.this.selectedRecipeIndex.get());
                }

                access.execute((level, pos) -> {
                    long gameTime = level.getGameTime();
                    if (WoodcutterMenu.this.lastSoundTime != gameTime) {
                        level.playSound(null, pos, ModSoundEvents.UI_WOODCUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        WoodcutterMenu.this.lastSoundTime = gameTime;
                    }
                });
                super.onTake(player, carried);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(WoodcutterMenu.this.inputSlot.getItem());
            }
        });
        this.addStandardInventorySlots(inventory, 8, 84);
        this.addDataSlot(this.selectedRecipeIndex);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public SelectableRecipe.SingleInputSet<WoodcuttingRecipe> getVisibleRecipes() {
        return this.recipesForInput;
    }

    public int getNumberOfVisibleRecipes() {
        return this.recipesForInput.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipesForInput.isEmpty();
    }

    @Override
    public boolean stillValid(final @NonNull Player player) {
        return stillValid(this.access, player, ModBlocks.WOODCUTTER);
    }

    @Override
    public boolean clickMenuButton(final @NonNull Player player, final int buttonId) {
        if (this.selectedRecipeIndex.get() == buttonId) {
            return false;
        } else {
            if (this.isValidRecipeIndex(buttonId)) {
                this.selectedRecipeIndex.set(buttonId);
                this.setupResultSlot(buttonId);
            }

            return true;
        }
    }

    private boolean isValidRecipeIndex(final int buttonId) {
        return buttonId >= 0 && buttonId < this.recipesForInput.size();
    }

    public void slotsChanged(final @NonNull Container container) {
        ItemStack input = this.inputSlot.getItem();
        if (!input.is(this.input.getItem())) {
            this.input = input.copy();
            this.setupRecipeList(input);
        }
    }

    private void setupRecipeList(final ItemStack item) {
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!item.isEmpty()) {
            this.recipesForInput = new SingleInputSet<>(
                    this.level.recipeAccess()
                    .getSynchronizedRecipes()
                    .getAllOfType(ModRecipeTypes.WOODCUTTING).stream()
                    .filter(holder -> holder.value().input().test(item))
                    .map(holder -> new SelectableRecipe.SingleInputEntry<>(
                            holder.value().input(), new SelectableRecipe<>(holder.value().resultDisplay(), Optional.of(holder))
                    ))
                    .toList());
        } else {
            this.recipesForInput = SingleInputSet.empty();
        }
    }

    private void setupResultSlot(final int index) {
        Optional<SelectableRecipe<WoodcuttingRecipe>> usedRecipe;
        if (!this.recipesForInput.isEmpty() && this.isValidRecipeIndex(index)) {
            SelectableRecipe.SingleInputEntry<WoodcuttingRecipe> entry = this.recipesForInput.entries().get(index);
            usedRecipe = Optional.of(entry.recipe());
        } else {
            usedRecipe = Optional.empty();
        }

        usedRecipe.ifPresentOrElse((recipe) -> {
            this.resultContainer.setRecipeUsed(recipe.recipe().get());
            this.resultSlot.set((recipe.recipe().get().value()).assemble(new SingleRecipeInput(this.container.getItem(0))));
        }, () -> {
            this.resultSlot.set(ItemStack.EMPTY);
            this.resultContainer.setRecipeUsed(null);
        });
        this.broadcastChanges();
    }

    public @NonNull MenuType<?> getType() {
        return MenuType.STONECUTTER;
    }

    public void registerUpdateListener(final Runnable slotUpdateListener) {
        this.slotUpdateListener = slotUpdateListener;
    }

    public boolean canTakeItemForPickAll(final @NonNull ItemStack carried, final Slot target) {
        return target.container != this.resultContainer && super.canTakeItemForPickAll(carried, target);
    }

    public @NonNull ItemStack quickMoveStack(final @NonNull Player player, final int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            Item item = stack.getItem();
            clicked = stack.copy();
            if (slotIndex == 1) {
                item.onCraftedBy(stack, player);
                if (!this.moveItemStackTo(stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, clicked);
            } else if (slotIndex == 0) {
                if (!this.moveItemStackTo(stack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.level.recipeAccess().stonecutterRecipes().acceptsInput(stack)) {
                if (!this.moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= 2 && slotIndex < 29) {
                if (!this.moveItemStackTo(stack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= 29 && slotIndex < 38 && !this.moveItemStackTo(stack, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (stack.getCount() == clicked.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
            if (slotIndex == 1) {
                player.drop(stack, false);
            }

            this.broadcastChanges();
        }
        return clicked;
    }

    public void removed(final @NonNull Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((_, _) -> this.clearContainer(player, this.container));
    }
}
