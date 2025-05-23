package net.starly.alphachest.util;

import net.starly.alphachest.AlphaChestMain;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EncodeUtil {

    public static byte[] encode(int slot, Inventory inventory) {
        if (inventory == null) return null;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); BukkitObjectOutputStream boos = new BukkitObjectOutputStream(bos)) {
            Map<String, Object> result = new HashMap<>();


            result.put("slot", slot);
            result.put("size", inventory.getSize());
            result.put("holder", inventory.getHolder());
            result.put("contents", inventory.getContents());


            boos.writeObject(result);

            return bos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Inventory decode(byte[] bytes) {
        if (bytes == null) return null;

        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); BukkitObjectInputStream bois = new BukkitObjectInputStream(bis)) {
            Map<String, Object> data = (Map<String, Object>) bois.readObject();


            int slot = (int) data.get("slot");
            int size = (int) data.get("size");
            InventoryHolder holder = (InventoryHolder) data.get("holder");
            ItemStack[] contents = (ItemStack[]) data.get("contents");


            Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(holder, size, "가상창고 [" + slot + "]");
            inventory.setContents(contents);

            return inventory;
        } catch (NullPointerException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
