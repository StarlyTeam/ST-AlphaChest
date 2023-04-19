package net.starly.alphachest.command;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.data.holder.AlphaChestHubInventoryDataHolder;
import net.starly.alphachest.util.GUIUtil;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AlphaChestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            player.openInventory(GUIUtil.getHubGUI(AlphaChestMain.getInstance().getServer().getPlayer("yejunho10").getUniqueId()));
            return true;
        }

        switch (args[0]) {
            default: {
                return true;
            }
        }
    }
}
