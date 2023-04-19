package net.starly.alphachest.data.holder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.starly.alphachest.enums.AlphaChestType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
public class AlphaChestInventoryDataHolder implements InventoryHolder, Serializable {

    @Getter
    private final UUID owner;
    @Getter
    private final AlphaChestType type;


    @Override
    public Inventory getInventory() {
        return null;
    }
}
