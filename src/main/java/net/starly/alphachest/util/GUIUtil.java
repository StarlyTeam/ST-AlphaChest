package net.starly.alphachest.util;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.data.holder.AlphaChestHubInventoryDataHolder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class GUIUtil {

    public static Inventory getHubGUI(UUID owner) {
        Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(new AlphaChestHubInventoryDataHolder(owner), 27, "허브 인벤토리");

        {
            ItemStack itemStack;
            try { itemStack = new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE")); }
            catch (Exception ignored) { itemStack = new ItemStack(Material.STAINED_GLASS_PANE, (short) 0, (byte) 13); }

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§r");
            itemStack.setItemMeta(itemMeta);

            ItemStack finalItemStack = itemStack;
            Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26).forEach(slot -> inventory.setItem(slot, finalItemStack));
        } {
            ItemStack itemStack = new ItemStack(Material.CHEST);

            Arrays.asList(11, 12, 13, 14, 15).forEach(slot -> {
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("§r§6" + (slot - 10) + "번 창고");
                itemStack.setItemMeta(itemMeta);

                inventory.setItem(slot, itemStack);
            });
        }

        return inventory;
    }
}
