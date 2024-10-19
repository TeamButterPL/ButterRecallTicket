package kr.dalkubi.ButterRecallTicket.listener;

import kr.dalkubi.ButterRecallTicket.ButterRecallTicket;
import kr.dalkubi.ButterRecallTicket.message.Message;
import kr.dalkubi.ButterRecallTicket.message.MessageKey;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN;

public class TicketClickListener implements Listener {

    Message msgData  = Message.getInstance();

    private final ButterRecallTicket plugin;
    private final Map<Player, Long> lastUsed;
    private final Map<Player, BukkitRunnable> cooldownTasks;

    public TicketClickListener(ButterRecallTicket plugin) {
        this.plugin = plugin;
        this.lastUsed = new HashMap<>();
        this.cooldownTasks = new HashMap<>();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (mainHand.isSimilar(plugin.getManageTicket().getTicket())) {
                if (plugin.getManageTicket().getLocation() == null) {
                    player.sendMessage(msgData.getMessage(MessageKey.LOCATION_NOT_SET));
                    return;
                }

                long currentTime = System.currentTimeMillis();
                long cooldownTime = plugin.getManageTicket().getCooldown() * 1000;

                if (lastUsed.containsKey(player) && currentTime - lastUsed.get(player) < cooldownTime) {
                    player.sendMessage(msgData.getMessage(MessageKey.COOLDOWN));
                    return;
                }

                lastUsed.put(player, currentTime);
                int totalCooldownTime = plugin.getManageTicket().getCooldown();
                startCooldownTimer(player, totalCooldownTime);

                player.sendMessage(msgData.getMessage(MessageKey.CLICK_TICKET));
                return;
            }
        }
    }

    private void startCooldownTimer(Player player, int totalCooldownTime) {
        if (cooldownTasks.containsKey(player)) {
            cooldownTasks.get(player).cancel();
        }

        BukkitRunnable task = new BukkitRunnable() {
            int remainingTime = totalCooldownTime;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    ItemStack ticket = plugin.getManageTicket().getTicket();
                    if (player.getInventory().contains(ticket.getType())) {
                        player.teleport(plugin.getManageTicket().getLocation(), PLUGIN);
                        player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                        player.sendMessage(msgData.getMessage(MessageKey.CLICK_TICKET));
                        player.getInventory().removeItem(ticket);

                        cooldownTasks.remove(player);
                        cancel();
                    } else {
                        player.sendMessage(msgData.getMessage(MessageKey.PLAYER_NOT_IN_HAND));
                        cooldownTasks.remove(player);
                        cancel();
                    }
                } else {
                    player.sendActionBar(ChatColor.GREEN + String.valueOf(remainingTime) + " 초 뒤 이동됩니다.");
                    remainingTime--;
                }
            }
        };
        task.runTaskTimer(plugin, 0, 20);
        cooldownTasks.put(player, task);
    }

}
