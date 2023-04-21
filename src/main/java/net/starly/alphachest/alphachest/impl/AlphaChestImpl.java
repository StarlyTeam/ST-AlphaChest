package net.starly.alphachest.alphachest.impl;

import net.starly.alphachest.alphachest.AlphaChest;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Range;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public class AlphaChestImpl implements AlphaChest {

    private final Inventory[] inventory = new Inventory[MAX_SLOT];



    @Override
    public Inventory getSlotInventory(@Range(from = 1, to = MAX_SLOT) int slot) {
        return inventory[slot - 1];
    }

    @Override
    public void setSlotInventory(@Range(from = 1, to = MAX_SLOT) int slot, Inventory inventory) {
        this.inventory[slot - 1] = inventory;
    }
}