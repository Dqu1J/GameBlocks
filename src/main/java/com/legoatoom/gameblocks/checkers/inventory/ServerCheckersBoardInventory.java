package com.legoatoom.gameblocks.checkers.inventory;

import com.legoatoom.gameblocks.checkers.items.ICheckersPieceItem;
import com.legoatoom.gameblocks.checkers.screen.slot.CheckersGridSlot;
import com.legoatoom.gameblocks.checkers.util.CheckersActionType;
import com.legoatoom.gameblocks.common.inventory.ServerBoardInventory;
import com.legoatoom.gameblocks.common.util.ActionType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;

import java.util.ArrayList;

import static com.legoatoom.gameblocks.registry.CheckersRegistry.BLACK_STONE;
import static com.legoatoom.gameblocks.registry.CheckersRegistry.WHITE_STONE;

public class ServerCheckersBoardInventory extends CheckersBoardInventory implements ServerBoardInventory<CheckersGridSlot> {

    public final ArrayList<ArrayPropertyDelegate> slotHintPropertyDelegate = new ArrayList<>();
    final CheckersGridSlot[] slots;
    final BlockEntity entity;

    public ServerCheckersBoardInventory(BlockEntity entity) {
        super(false);
        for (int i = 0; i < boardSize; i++) {
            this.slotHintPropertyDelegate.add(new ArrayPropertyDelegate(boardSize));
        }
        slots = new CheckersGridSlot[boardSize];
        this.entity = entity;
    }

    @Override
    public ArrayList<ArrayPropertyDelegate> getSlotHintsPropertyDelgates() {
        return slotHintPropertyDelegate;
    }

    @Override
    public CheckersGridSlot[] getSlots() {
        return slots;
    }

    @Override
    public ActionType getDefaultHint() {
        return CheckersActionType.NONE;
    }

    @Override
    public void setSlot(int index, CheckersGridSlot slot) {
        this.slots[index] = slot;
    }

    @Override
    public CheckersGridSlot getSlot(int index) {
        return this.slots[index];
    }

    @Override
    public void fillWithDefaultPieces() {
        this.setStack(boardSize + WHITE_STONE.getStorageIndex(), new ItemStack(WHITE_STONE, WHITE_STONE.getMaxCount()));
        this.setStack(boardSize + BLACK_STONE.getStorageIndex(), new ItemStack(BLACK_STONE, BLACK_STONE.getMaxCount()));
    }

    /**
     * Very important, it makes sure that the inventory is stored.
     */
    @Override
    public void markDirty() {
        var entity = getEntity();
        if (getEntity().getWorld() != null) {
            entity.markDirty();
        }
    }

    @Override
    public boolean canDropPackage() {
        int white = 20, black = 20;
        for (ItemStack stack : getItems()) {
            if (stack.getItem() instanceof ICheckersPieceItem item) {
                boolean isBlack = item.isBlack();
                switch (item.getType()) {
                    case STONE, KING -> {
                        if (isBlack) {
                            black -= stack.getCount();
                        } else {
                            white -= stack.getCount();
                        }
                    }
                }
            }
        }
        if (black != 0) return false;
        if (white != 0) return false;
        return true;
    }

    public BlockEntity getEntity() {
        return entity;
    }
}
