package net.starly.alphachest.command.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static net.starly.alphachest.AlphaChestMain.MAX_SLOT;

public class AlphaChestTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("starly.alphachest.open")) completions.add("열기");
            if (sender.hasPermission("starly.alphachest.per.give") || sender.hasPermission("starly.alphachest.per.take")) completions.add("권한");
        } else if (args.length == 2) {
            if (args[0].equals("열기")) return null;
            else if (args[0].equals("권한")) {
                completions.add("<액션>");
                if (sender.hasPermission("starly.alphachest.per.give")) completions.add("지급");
                if (sender.hasPermission("starly.alphachest.per.take")) completions.add("뺏기");
            }
        } else if (args.length == 3) {
            if (args[0].equals("권한")) return null;
        } else if (args.length == 4) {
            completions.add("<슬롯>");
            if (args[0].equals("권한")) {
                for (int i = 1; i <= MAX_SLOT; i++) completions.add(String.valueOf(i));
            }
        }

        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}
