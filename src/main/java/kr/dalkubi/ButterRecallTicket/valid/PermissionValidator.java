package kr.dalkubi.ButterRecallTicket.valid;

import kr.dalkubi.ButterRecallTicket.message.Message;
import kr.dalkubi.ButterRecallTicket.message.MessageKey;
import org.bukkit.entity.Player;

public class PermissionValidator {

    public static Boolean hasPermission(Player player, String permission) {
        Message msgData = Message.getInstance();
        if (player.hasPermission(" " + permission)) {
            return true;
        } else {
            player.sendMessage(msgData.getMessage(MessageKey.NO_PERMISSION));
            return false;
        }
    }
}