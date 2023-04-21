package net.starly.alphachest.inventory.holder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.starly.alphachest.AlphaChestMain;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

@AllArgsConstructor
public class AlphaChestInventoryHolder implements InventoryHolder {

    @Getter
    private final UUID owner;
    @Getter
    private final int slot;

    @Override
    public Inventory getInventory() {
        return AlphaChestMain.getAlphaChestRepository().getPlayerAlphaChest(owner).getSlotInventory(slot);
    }
}
