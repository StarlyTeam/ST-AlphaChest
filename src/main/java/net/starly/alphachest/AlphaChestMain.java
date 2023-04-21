package net.starly.alphachest;

import net.starly.alphachest.alphachest.AlphaChest;
import net.starly.alphachest.alphachest.impl.AlphaChestImpl;
import net.starly.alphachest.repo.AlphaChestRepository;
import net.starly.alphachest.repo.impl.AlphaChestRepositoryImpl;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlphaChestMain extends JavaPlugin {

    private static AlphaChestMain instance;
    public static AlphaChestMain getInstance() {
        return instance;
    }

    private static AlphaChestRepository alphaChestRepository;
    public static AlphaChestRepository getAlphaChestRepository() {
        return alphaChestRepository;
    }

    public final static int MAX_SLOT = 5;


    @Override
    public void onEnable() {
        /* DEPENDENCY
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        if (!isPluginEnabled("ST-Core")) {
            getServer().getLogger().warning("[" + getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            getServer().getLogger().warning("[" + getName() + "] 다운로드 링크 : §fhttp://starly.kr/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        /* SETUP
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        instance = this;
        alphaChestRepository = new AlphaChestRepositoryImpl();
//        new Metrics(this, 12345); // TODO: 수정

        /* CONFIG
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
        if (!new File(getDataFolder(), "players/").exists()) saveResource("players/", true);
        alphaChestRepository.initializing(new File(getDataFolder(), "players/"));

        /* COMMAND
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
//        getServer().getPluginCommand("가상창고").setExecutor(new AlphaChestCmd());

        /* LISTENER
         ──────────────────────────────────────────────────────────────────────────────────────────────────────────────── */
//        getServer().getPluginManager().registerEvents(new InventoryListener(), instance);
    }

    @Override
    public void onDisable() {
        alphaChestRepository.saveAll();
    }

    private boolean isPluginEnabled(String name) {
        Plugin plugin = getServer().getPluginManager().getPlugin(name);
        return plugin != null && plugin.isEnabled();
    }
}
