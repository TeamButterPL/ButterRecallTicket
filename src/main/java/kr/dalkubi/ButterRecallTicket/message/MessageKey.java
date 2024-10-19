package kr.dalkubi.ButterRecallTicket.message;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum MessageKey {
    /* --------------- NORMAL ---------------*/
    PREFIX("normal.prefix"),
    RELOAD_CONFIG("normal.reload_config"),

    /* --------------- ERROR ---------------*/
    PLAYER_ONLY("error.player_only"),
    NO_PERMISSION("error.no_permission"),
    WRONG_COMMAND("error.wrong_command"),

    /* --------------- MAIN ---------------*/
    GAT_TICKET("main.gat_ticket"),

    CLICK_TICKET("main.click_ticket"),

    PLAYER_NOT_IN_HAND("main.player_not_in_hand"),

    COOLDOWN("main.cooldown"),

    LOCATION_SET("main.location_set"),
    LOCATION_NOT_SET("main.location_not_set");

    private final String key;
}
