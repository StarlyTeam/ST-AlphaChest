package net.starly.alphachest.command;

import net.starly.alphachest.AlphaChestMain;
import net.starly.alphachest.inventory.GUIUtil;
import net.starly.alphachest.repo.AlphaChestRepository;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public class AlphaChestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            // OPEN HUB
            player.openInventory(GUIUtil.getHubGUI(player.getUniqueId()));
            return true;
        }

        switch (args[0]) {
            case "열기": {
                if (args.length == 1) {
                    player.sendMessage("자신의 가상창고를 확인하려면 [/가상창고]를 사용해주세요.");
                    return true;
                } else if (args.length == 2) {
                    OfflinePlayer target = AlphaChestMain.getInstance().getServer().getOfflinePlayer(args[1]);
                    if (!(target.isOnline() || target.hasPlayedBefore())) {
                        player.sendMessage("입력하신 플레이어는 존재하지 않습니다.");
                        return true;
                    }

                    player.openInventory(GUIUtil.getHubGUI(target.getUniqueId()));
                    return true;
                } else {
                    player.sendMessage("잘못된 명령어입니다.");
                    return true;
                }
            }

            case "권한": {
                if (args.length == 1) {
                    player.sendMessage("관리 액션을 입력해주세요.");
                    return true;
                } else if (args.length == 2) {
                    player.sendMessage("권한을 지급할 플레이어를 입력해주세요.");
                    return true;
                } else if (args.length == 3) {
                    player.sendMessage("권한을 지급할 슬롯을 입력해주세요.");
                    return true;
                } else if (args.length != 4) {
                    player.sendMessage("잘못된 명령어입니다.");
                    return true;
                }

                OfflinePlayer target = AlphaChestMain.getInstance().getServer().getOfflinePlayer(args[2]);
                if (!(target.isOnline() || target.hasPlayedBefore())) {
                    player.sendMessage("입력하신 플레이어는 존재하지 않습니다.");
                    return true;
                }

                int slot;
                try {
                    slot = Integer.parseInt(args[3]);
                } catch (NumberFormatException ignored) {
                    player.sendMessage("슬롯이 잘못되었습니다.");
                    return true;
                }
                if (!(slot > 0 && slot <= MAX_SLOT)) {
                    player.sendMessage("슬롯이 잘못되었습니다.");
                    return true;
                }


                AlphaChestRepository alphaChestRepository = AlphaChestMain.getAlphaChestRepository();

                switch (args[1]) {
                    case "지급": {
                        AlphaChestMain.getAlphaChestRepository().setUsable(target.getUniqueId(), slot, true);
                        player.sendMessage("성공적으로 권한을 지급했습니다.");
                        return true;
                    }

                    case "뺏기": {
                        alphaChestRepository.getPlayerAlphaChest(target.getUniqueId()).getSlotInventory(slot).getViewers().forEach(HumanEntity::closeInventory);
                        alphaChestRepository.setUsable(target.getUniqueId(), slot, false);
                        player.sendMessage("성공적으로 권한을 뺏었습니다.");
                        return true;
                    }

                    default: {
                        player.sendMessage("잘못된 명령어입니다.");
                        return true;
                    }
                }
            }

            default: {
                player.sendMessage("잘못된 명령어입니다.");
                return true;
            }
        }
    }
}
