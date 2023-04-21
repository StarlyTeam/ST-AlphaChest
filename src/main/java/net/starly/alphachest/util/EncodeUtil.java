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

    public static byte[] encode(Inventory inventory) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); BukkitObjectOutputStream boos = new BukkitObjectOutputStream(bos)) {
            Map<String, Object> result = new HashMap<>();


            Object titleComponent = inventory.getClass().getDeclaredMethod("title").invoke(inventory);
            Field titleContentField = titleComponent.getClass().getDeclaredField("content");
            titleContentField.setAccessible(true);
            String title = (String) titleContentField.get(titleComponent);
            titleContentField.setAccessible(false);

            result.put("title", title);
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
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); BukkitObjectInputStream bois = new BukkitObjectInputStream(bis)) {
            Map<String, Object> data = (Map<String, Object>) bois.readObject();


            String title = (String) data.get("title");
            int size = (int) data.get("size");
            InventoryHolder holder = (InventoryHolder) data.get("holder");
            ItemStack[] contents = (ItemStack[])data.get("contents");


            Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(holder, size, title);
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
