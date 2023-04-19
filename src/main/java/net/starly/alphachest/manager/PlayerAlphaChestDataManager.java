package net.starly.alphachest.manager;

import net.starly.alphachest.data.PlayerAlphaChestData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerAlphaChestDataManager {

    private static PlayerAlphaChestDataManager instance;
    public static PlayerAlphaChestDataManager getInstance() {
        if (instance == null) instance = new PlayerAlphaChestDataManager();
        return instance;
    }


    private final Map<UUID, PlayerAlphaChestData> dataMap;

    private PlayerAlphaChestDataManager() {
        this.dataMap = new HashMap<>();
    }


    public PlayerAlphaChestData getData(UUID uniqueId) {
        if (!hasData(uniqueId)) dataMap.put(uniqueId, new PlayerAlphaChestData(uniqueId));
        return dataMap.get(uniqueId);
    }

    public void removeData(UUID uniqueId) {
        dataMap.remove(uniqueId);
    }

    public boolean hasData(UUID uniqueId) {
        return dataMap.containsKey(uniqueId);
    }
}