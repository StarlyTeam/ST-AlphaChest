package net.starly.alphachest.inventory.holder;

import lombok.Data;
import net.starly.alphachest.AlphaChestMain;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AlphaChestInventoryHolder implements InventoryHolder, Serializable {

    private final UUID owner;
    private final int slot;

    @Override
    public Inventory getInventory() {
        return AlphaChestMain.getAlphaChestRepository().getPlayerAlphaChest(owner).getSlotInventory(slot);
    }
}
