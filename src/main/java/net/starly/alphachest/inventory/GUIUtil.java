package net.starly.alphachest.inventory;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.inventory.holder.AlphaChestInventoryHolder;
import net.starly.core.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class GUIUtil {

    public static Inventory getHubGUI(UUID owner) {
        Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(new AlphaChestInventoryHolder(owner, 0), 27, "가상창고 [허브]");

        {
            ItemStack emptySlotStack;
            try {
                emptySlotStack = new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE"));
            } catch (IllegalArgumentException ignored) {
                emptySlotStack = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), (short) 0, (byte) 13);
            }

            ItemMeta emptyStackMeta = emptySlotStack.getItemMeta();
            emptyStackMeta.setDisplayName("§r");
            emptySlotStack.setItemMeta(emptyStackMeta);
            for (int i = 0; i <= 9; i++) inventory.setItem(i, emptySlotStack);
            for (int i = 17; i <= 26; i++) inventory.setItem(i, emptySlotStack);
        }
        {
            ItemBuilder itemBuilder = new ItemBuilder(Material.CHEST);
            for (int i = 11; i <= 15; i++) inventory.setItem(i, itemBuilder.setDisplayName("§6" + (i - 10) + "번 창고").build()); // TODO : 슬롯 수 변경시 수정 필요
        }


        return inventory;
    }
}
