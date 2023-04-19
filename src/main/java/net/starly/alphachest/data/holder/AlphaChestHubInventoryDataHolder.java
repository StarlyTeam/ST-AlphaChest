package net.starly.alphachest.data.holder;

import java.io.Serializable;
import java.util.UUID;

public class AlphaChestHubInventoryDataHolder extends AlphaChestInventoryDataHolder implements Serializable {

    public AlphaChestHubInventoryDataHolder(UUID owner) {
        super(owner, null);
    }
}
