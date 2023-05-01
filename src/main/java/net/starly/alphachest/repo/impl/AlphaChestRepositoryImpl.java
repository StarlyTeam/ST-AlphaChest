package net.starly.alphachest.repo.impl;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.alphachest.AlphaChest;
import net.starly.alphachest.alphachest.impl.AlphaChestImpl;
import net.starly.alphachest.inventory.holder.AlphaChestInventoryHolder;
import net.starly.alphachest.repo.AlphaChestRepository;
import net.starly.alphachest.util.EncodeUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public class AlphaChestRepositoryImpl implements AlphaChestRepository {

    private File playersFolder;
    private final Map<UUID, AlphaChest> alphaChestMap = new HashMap<>();


    @Override
    @Deprecated
    public void initialize(File playersFolder) {
        this.alphaChestMap.clear();
        this.playersFolder = playersFolder;

        if (!playersFolder.exists()) playersFolder.mkdirs();
        else {
            for (File playerFile : playersFolder.listFiles()) {
                FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

                UUID uniqueId = UUID.fromString(playerFile.getName().replace(".yml", ""));
                if (!alphaChestMap.containsKey(uniqueId)) alphaChestMap.put(uniqueId, new AlphaChestImpl());

                playerConfig.getKeys(false).forEach(slotStr -> {
                    int slot = Integer.parseInt(slotStr);
                    ConfigurationSection section = playerConfig.getConfigurationSection(slotStr);

                    getPlayerAlphaChest(uniqueId).setSlotInventory(slot, EncodeUtil.decode((byte[]) section.get("inventory")));
                    setUsable(uniqueId, slot, section.getBoolean("usable"));
                });
            }
        }
    }

    @Override
    public AlphaChest getPlayerAlphaChest(UUID uniqueId) {
        if (!alphaChestMap.containsKey(uniqueId)) alphaChestMap.put(uniqueId, new AlphaChestImpl());
        return alphaChestMap.get(uniqueId);
    }

    @Override
    public void setUsable(UUID uniqueId, int slot, boolean usable) {
        AlphaChest alphaChest = getPlayerAlphaChest(uniqueId);

        if (!usable) {
            alphaChest.setSlotInventory(slot, null);
        } else if (alphaChest.getSlotInventory(slot) == null) {
            Inventory inventory = AlphaChestMain.getInstance().getServer().createInventory(new AlphaChestInventoryHolder(uniqueId, slot), 54, "가상창고 [" + slot + "번]");
            alphaChest.setSlotInventory(slot, inventory);
        }
    }

    @Override
    public boolean isUsable(UUID uniqueId, int slot) {
        AlphaChest alphaChest = getPlayerAlphaChest(uniqueId);
        return alphaChest.getSlotInventory(slot) != null;
    }

    @Override
    public void saveAll() {
        for (Map.Entry<UUID, AlphaChest> entry : alphaChestMap.entrySet()) {
            UUID uniqueId = entry.getKey();
            AlphaChest alphaChest = entry.getValue();

            File configFile = new File(playersFolder, uniqueId + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

            for (int slot = 1; slot <= MAX_SLOT; slot++) {
                ConfigurationSection section = config.createSection(String.valueOf(slot));
                section.set("inventory", EncodeUtil.encode(slot, alphaChest.getSlotInventory(slot)));
                section.set("usable", isUsable(uniqueId, slot));
            }

            try {
                config.save(configFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}