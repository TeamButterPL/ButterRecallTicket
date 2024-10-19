package kr.dalkubi.ButterRecallTicket.command;

import kr.dalkubi.ButterRecallTicket.ButterRecallTicket;
import kr.dalkubi.ButterRecallTicket.message.Message;
import kr.dalkubi.ButterRecallTicket.message.MessageKey;
import kr.dalkubi.ButterRecallTicket.valid.PermissionValidator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecallTicketCmd implements CommandExecutor {

    Message msgData  = Message.getInstance();

    private final ButterRecallTicket plugin;

    public RecallTicketCmd(ButterRecallTicket plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("귀환서")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(msgData.getMessage(MessageKey.PLAYER_ONLY));
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
            return false;
        }

        switch (args[0]) {
            case "획득": // 아이템 획득 명령어
                if (!PermissionValidator.hasPermission(player, "gat")) return false;

                player.getInventory().addItem(plugin.getManageTicket().getTicket());
                player.sendMessage(msgData.getMessage(MessageKey.GAT_TICKET));
                return true;

            case "위치설정": // 위치 설정 명령어
                if (!PermissionValidator.hasPermission(player, "create")) return false;

                plugin.getManageTicket().setTicketLocation(player.getLocation());
                player.sendMessage(msgData.getMessage(MessageKey.LOCATION_SET));

                return true;

            case "리로드": // 설정 리로드 명령어
                if (!PermissionValidator.hasPermission(player, "reload")) return false;

                plugin.reloadConfig();
                plugin.getManageTicket().loadScrollData();
                plugin.reloadMessageConfig();
                player.sendMessage(msgData.getMessage(MessageKey.RELOAD_CONFIG));
                return true;

            default:
                player.sendMessage("해당 명령어는 존재하지 않습니다.");
                player.sendMessage(msgData.getMessage(MessageKey.WRONG_COMMAND));
                return false;
        }
    }
}
