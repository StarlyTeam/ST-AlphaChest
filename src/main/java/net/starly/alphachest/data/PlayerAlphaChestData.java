package net.starly.alphachest.data;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.data.holder.AlphaChestInventoryDataHolder;
import net.starly.alphachest.enums.AlphaChestType;
import net.starly.alphachest.util.EncodeUtil;
import net.starly.core.util.collection.STList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerAlphaChestData {

    private final Map<AlphaChestType, Inventory> inventoryMap;
    private final Map<AlphaChestType, Boolean> permissionMap;

    private File configFile;
    private final FileConfiguration config;

    private final UUID owner;


    public PlayerAlphaChestData(UUID owner) {
        this.inventoryMap = new HashMap<>();
        this.permissionMap = new HashMap<>();

        this.configFile = new File(AlphaChestMain.getInstance().getDataFolder(), "data/" + owner + ".yml");
        this.config = new YamlConfiguration();

        this.owner = owner;

        loadConfig();
        loadData();
    }


    public STList<ItemStack> getItems(AlphaChestType type) {
        return new STList<>(inventoryMap.get(type).getContents());
    }

    public void setItems(AlphaChestType type, STList<ItemStack> items) {
        getInventory(type).setContents(items.toList().toArray(new ItemStack[54]));
        saveData();
    }

    public Inventory getInventory(AlphaChestType type) {
        if (inventoryMap.get(type) == null) {
            Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(new AlphaChestInventoryDataHolder(owner, type), 54, "가상창고 [" + type.getValue() + "]");
            inventoryMap.put(type, inventory);
        }
        return inventoryMap.get(type);
    }

    public boolean isUsable(AlphaChestType type) {
        if (!permissionMap.containsKey(type)) setUsable(type, false);
        return permissionMap.get(type);
    }

    public void setUsable(AlphaChestType type, boolean b) {
        permissionMap.remove(type);
        permissionMap.put(type, b);
        saveData();
    }


    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadConfig() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            this.configFile = new File(AlphaChestMain.getInstance().getDataFolder(), "data/" + owner + ".yml");
        }

        try {
            config.load(configFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        Arrays.stream(AlphaChestType.values())
                .forEach(type -> {
                    Inventory inventory = EncodeUtil.decode((byte[]) config.get("inv." + type.name()));
                    if (inventory != null) inventoryMap.put(type, inventory);

                    if (config.isBoolean("permission." + type.name())) permissionMap.put(type, config.getBoolean("permission." + type.name()));
                });
    }

    public void saveData() {
        inventoryMap.forEach((type, data) -> {
            if (data == null) return;
            config.set("inv." + type.name(), EncodeUtil.encode(data));
        });
        permissionMap.forEach((type, b) -> {
            if (b == null) return;
            config.set("permission." + type.name(), b);
        });

        saveConfig();
    }
}