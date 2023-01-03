package net.somberfob.vikingmod.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.somberfob.vikingmod.screen.fishingtrap.FishingTrapMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class FishingTrapBlockEntity extends BlockEntity implements MenuProvider {
    public int slotAmount = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 78;

    private final ItemStackHandler itemHandler = new ItemStackHandler(slotAmount) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public FishingTrapBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FISHING_TRAP.get(), blockPos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FishingTrapBlockEntity.this.progress;
                    case 1 -> FishingTrapBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FishingTrapBlockEntity.this.progress = value;
                    case 1 -> FishingTrapBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Fishing Trap");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new FishingTrapMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTagNbt) {
        compoundTagNbt.put("inventory", itemHandler.serializeNBT());
        compoundTagNbt.putInt("fishing_trap.progress", this.progress);
        super.saveAdditional(compoundTagNbt);
    }

    @Override
    public void load(CompoundTag compoundTagNbt) {
        itemHandler.deserializeNBT(compoundTagNbt.getCompound("inventory"));
        this.progress = compoundTagNbt.getInt("fishing_trap.progress");
        super.load(compoundTagNbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState,
                            FishingTrapBlockEntity entity) {

        if (level.isClientSide()) {
            return;
        }

        entity.progress++;
        setChanged(level, blockPos, blockState);

        if (entity.progress >= entity.maxProgress) {
            if (isInShallowWater(blockPos, level)) {
                ItemStack fishingLoot = getFishingLoot(level, blockPos);
                startFishing(entity, level, blockPos, fishingLoot);
            }
            resetProgress(entity);
        }
    }

    private static void resetProgress(FishingTrapBlockEntity entity) {
        entity.progress = 0;
    }

    private static void startFishing(FishingTrapBlockEntity entity, Level level, BlockPos blockPos, ItemStack itemStack) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        int slotID = inventory.getContainerSize();

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));

            if (canFish(blockPos, level, itemStack, i, inventory)) {
                slotID = i;
            }
        }

        if (slotID < inventory.getContainerSize()) {
            entity.itemHandler.setStackInSlot(slotID, new ItemStack(itemStack.getItem(), entity.itemHandler.getStackInSlot(slotID).getCount() + 1));
        }

        resetProgress(entity);
    }

    private static boolean canFish(BlockPos blockPos, Level level, ItemStack itemStack, int pindex, SimpleContainer inventory) {
        return canInsertAmountIntoOutputSlot(inventory, pindex) && canInsertItemInOutputSlot(inventory, itemStack, pindex);
    }

    private static boolean isInShallowWater(BlockPos blockPos, Level level) {
        List<Boolean> list = new ArrayList<>();
        int allowedWaterDistance = 1;
        int disallowedWaterDistance = 3;

        for (Direction direction : Direction.values()) {
            if (level.isWaterAt(blockPos.relative(direction, allowedWaterDistance)) && direction != Direction.DOWN) {
                list.add(true);
            }

            if (!level.isWaterAt(blockPos.relative(direction, disallowedWaterDistance))) {
                list.add(true);
            }
        }

        if (list.isEmpty() || list.size() < 11) {
            return false;
        }

        return allTrue(list);
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory, int pIndex) {
        return inventory.getItem(pIndex).getMaxStackSize() > inventory.getItem(pIndex).getCount();
    }

    private static boolean canInsertItemInOutputSlot(SimpleContainer inventory, ItemStack itemStack, int pIndex) {
        return inventory.getItem(pIndex).getItem() == itemStack.getItem() || inventory.getItem(pIndex).isEmpty();
    }

    private static ItemStack getFishingLoot(Level level, BlockPos blockPos) {
        Vec3 postion = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        LootTable fishingLootTable = level.getServer().getLootTables().get(BuiltInLootTables.FISHING);
        LootContext.Builder builder = (new LootContext.Builder((ServerLevel)level))
                .withParameter(LootContextParams.TOOL, new ItemStack(Items.FISHING_ROD))
                .withParameter(LootContextParams.ORIGIN, postion);

        return fishingLootTable.getRandomItems(builder.create(LootContextParamSets.FISHING)).get(0);
    };

    private static boolean allTrue (List<Boolean> values) {
        for (boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }

}
