package net.starly.alphachest.alphachest;

import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Range;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public interface AlphaChest {
    Inventory getSlotInventory(@Range(from = 1, to = MAX_SLOT) int slot);

    void setSlotInventory(@Range(from = 1, to = MAX_SLOT) int slot, Inventory inventory);
}
