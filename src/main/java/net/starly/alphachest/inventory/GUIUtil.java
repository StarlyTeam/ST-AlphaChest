package net.starly.alphachest.inventory;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.inventory.holder.AlphaChestInventoryHolder;
import net.starly.core.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GUIUtil {

    public static Inventory getHubGUI(UUID owner) {
        Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(new AlphaChestInventoryHolder(owner, 0), 27, "가상창고 [허브]");

        {
            Material emptyType;
            try {
                emptyType = Material.valueOf("BLACK_STAINED_GLASS_PANE");
            } catch (NoSuchFieldError ignored) {
                emptyType = Material.valueOf("STAINED_GLASS_PANE");
            }

            ItemStack emptySlot = new ItemBuilder(emptyType)
                    .setDisplayName("§r")
                    .build();
            for (int i = 0; i <= 9; i++) inventory.setItem(i, emptySlot);
            for (int i = 17; i <= 26; i++) inventory.setItem(i, emptySlot);
        }
        {
            ItemBuilder itemBuilder = new ItemBuilder(Material.CHEST);
            for (int i = 11; i <= 15; i++) inventory.setItem(i, itemBuilder.setDisplayName("§6" + (i - 10) + "번 창고").build()); // TODO : 슬롯 수 변경시 수정 필요
        }


        return inventory;
    }
}
