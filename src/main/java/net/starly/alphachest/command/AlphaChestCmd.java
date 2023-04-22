package net.starly.alphachest.command;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.alphachest.AlphaChest;
import net.starly.alphachest.context.MessageContext;
import net.starly.alphachest.enums.MessageType;
import net.starly.alphachest.inventory.GUIUtil;
import net.starly.alphachest.repo.AlphaChestRepository;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public class AlphaChestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        MessageContext messageContext = MessageContext.getInstance();


        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "noConsoleCommand"));
                return true;
            }
            Player player = (Player) sender;

            player.openInventory(GUIUtil.getHubGUI(player.getUniqueId()));
            return true;
        }

        switch (args[0]) {
            case "열기": {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "noConsoleCommand"));
                    return true;
                }
                Player player = (Player) sender;

                if (args.length == 1) {
                    player.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "noPlayerName"));
                    return true;
                } else if (args.length == 2) {
                    OfflinePlayer target = AlphaChestMain.getInstance().getServer().getOfflinePlayer(args[1]);
                    if (!(target.isOnline() || target.hasPlayedBefore())) {
                        player.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "playerNotFound"));
                        return true;
                    }

                    player.openInventory(GUIUtil.getHubGUI(target.getUniqueId()));
                    return true;
                } else {
                    player.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "wrongCommand"));
                    return true;
                }
            }

            case "권한": {
                if (args.length == 1) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "noManageAction"));
                    return true;
                } else if (args.length == 2) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "noPlayerName"));
                    return true;
                } else if (args.length == 3) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "noSlot"));
                    return true;
                } else if (args.length != 4) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "wrongCommand"));
                    return true;
                }

                OfflinePlayer target = AlphaChestMain.getInstance().getServer().getOfflinePlayer(args[2]);
                if (!(target.isOnline() || target.hasPlayedBefore())) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "playerNotFound"));
                    return true;
                }

                int slot;
                try {
                    slot = Integer.parseInt(args[3]);
                } catch (NumberFormatException ignored) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "wrongSlot"));
                    return true;
                }
                if (!(slot > 0 && slot <= MAX_SLOT)) {
                    sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "wrongSlot"));
                    return true;
                }


                AlphaChestRepository alphaChestRepository = AlphaChestMain.getAlphaChestRepository();

                switch (args[1]) {
                    case "지급": {
                        AlphaChestMain.getAlphaChestRepository().setUsable(target.getUniqueId(), slot, true);
                        sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.NORMAL, "permissionGive"));
                        return true;
                    }

                    case "뺏기": {
                        if (alphaChestRepository.isUsable(target.getUniqueId(), slot)) new ArrayList<>(alphaChestRepository.getPlayerAlphaChest(target.getUniqueId()).getSlotInventory(slot).getViewers()).forEach(HumanEntity::closeInventory);
                        alphaChestRepository.setUsable(target.getUniqueId(), slot, false);
                        sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.NORMAL, "permissionTake"));
                        return true;
                    }

                    default: {
                        sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "wrongCommand"));
                        return true;
                    }
                }
            }

            default: {
                sender.sendMessage(messageContext.getMessageAfterPrefix(MessageType.ERROR, "wrongCommand"));
                return true;
            }
        }
    }
}
