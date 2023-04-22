package net.starly.alphachest.listener;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.inventory.holder.AlphaChestInventoryHolder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;
        if (!(inventory.getHolder() instanceof AlphaChestInventoryHolder)) {
            Inventory topInventory = player.getOpenInventory().getTopInventory();
            if (topInventory == null) return;
            if (!(topInventory.getHolder() instanceof AlphaChestInventoryHolder)) return;
            if (((AlphaChestInventoryHolder) topInventory.getHolder()).getSlot() != 0) return;

            event.setCancelled(true);
            return;
        }

        AlphaChestInventoryHolder holder = (AlphaChestInventoryHolder) inventory.getHolder();
        if (holder.getSlot() == 0) {
            // HUB INVENTORY
            event.setCancelled(true);

            int slot = event.getSlot() - 10;
            if (!(slot > 0 && slot <= MAX_SLOT)) return;
            if (!AlphaChestMain.getAlphaChestRepository().isUsable(holder.getOwner(), slot)) {
                player.sendMessage("해당 슬롯은 사용할 수 없습니다.");
                return;
            }

            player.openInventory(AlphaChestMain.getAlphaChestRepository().getPlayerAlphaChest(holder.getOwner()).getSlotInventory(slot));
        } else {
            // ALPHA-CHEST SLOT INVENTORY
            player.getOpenInventory().getTopInventory().getViewers().stream().map(entity -> (Player) entity).filter(player_ -> player_ != player).forEach(Player::updateInventory);
            AlphaChestMain.getAlphaChestRepository().saveAll();
        }
    }
}
