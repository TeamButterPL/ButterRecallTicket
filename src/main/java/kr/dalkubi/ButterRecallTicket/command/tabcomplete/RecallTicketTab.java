package kr.dalkubi.ButterRecallTicket.command.tabcomplete;

import kr.dalkubi.ButterRecallTicket.valid.PermissionValidator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecallTicketTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (label.equalsIgnoreCase("귀환서")) {
                if (PermissionValidator.hasPermission((Player) sender, "gat")) completions.add("획득");
                if (PermissionValidator.hasPermission((Player) sender, "create")) completions.add("위치설정");
                if (PermissionValidator.hasPermission((Player) sender, "reload")) completions.add("리로드");
            }
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}
