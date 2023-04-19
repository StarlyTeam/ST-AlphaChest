package net.starly.alphachest.listener;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.data.PlayerAlphaChestData;
import net.starly.alphachest.data.holder.AlphaChestInventoryDataHolder;
import net.starly.alphachest.enums.AlphaChestType;
import net.starly.alphachest.manager.PlayerAlphaChestDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        int slot = event.getSlot();

        if (player == null) return;
        if (inventory == null) return;


        if (inventory.getHolder() instanceof AlphaChestInventoryDataHolder) {
            AlphaChestInventoryDataHolder holder = (AlphaChestInventoryDataHolder) inventory.getHolder();

            if (holder.getType() == null) {
                // INVENTORY HUB
                event.setCancelled(true);

                if (inventory != player.getInventory()) {
                    if (!(slot >= 11 && slot <= 15)) return;
                    AlphaChestType type = AlphaChestType.valueOf(slot - 10);
                    if (type == null) return;

                    PlayerAlphaChestData targetData = PlayerAlphaChestDataManager.getInstance().getData(holder.getOwner());
                    if (!targetData.isUsable(type)) {
                        player.sendMessage("해당 슬롯은 사용할 수 없습니다.");
                        return;
                    }

                    player.openInventory(targetData.getInventory(type));
                }
            } else {
                // INVENTORY
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        PlayerAlphaChestData data = PlayerAlphaChestDataManager.getInstance().getData(holder.getOwner());
                        data.saveData();
                        data.getInventory(holder.getType()).getViewers().stream().map(humanEntity -> (Player) humanEntity).forEach(Player::updateInventory);
                    }
                }.runTaskLater(AlphaChestMain.getInstance(), 1L);
            }
        }
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
        Inventory inventory = event.getDestination();
        if (inventory == null) return;

        if (inventory.getHolder() instanceof AlphaChestInventoryDataHolder) {
            AlphaChestInventoryDataHolder holder = (AlphaChestInventoryDataHolder) inventory.getHolder();

            if (holder.getType() != null) {
                // INVENTORY
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        PlayerAlphaChestData data = PlayerAlphaChestDataManager.getInstance().getData(holder.getOwner());
                        data.saveData();
                        data.getInventory(holder.getType()).getViewers().stream().map(humanEntity -> (Player) humanEntity).forEach(Player::updateInventory);
                    }
                }.runTaskLater(AlphaChestMain.getInstance(), 1L);
            }
        }
    }
}
