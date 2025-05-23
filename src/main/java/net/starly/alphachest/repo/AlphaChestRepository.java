package net.starly.alphachest.repo;

import net.starly.alphachest.alphachest.AlphaChest;

import java.io.File;
import java.util.UUID;

public interface AlphaChestRepository {
    @Deprecated
    void initialize(File playersFolder);

    AlphaChest getPlayerAlphaChest(UUID uniqueId);


    void setUsable(UUID uniqueId, int slot, boolean usable);

    boolean isUsable(UUID uniqueId, int slot);

    void saveAll();
}
