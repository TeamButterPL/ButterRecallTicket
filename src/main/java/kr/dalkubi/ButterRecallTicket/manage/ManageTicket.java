package kr.dalkubi.ButterRecallTicket.manage;

import kr.dalkubi.ButterRecallTicket.ButterRecallTicket;
import kr.dalkubi.ButterRecallTicket.util.ColorCode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ManageTicket {

    private final ButterRecallTicket plugin;
    private ItemStack ticket;
    private Location location;
    private int cooldown;

    public ManageTicket(ButterRecallTicket plugin) {
        this.plugin = plugin;
        loadScrollData();
    }

    public void loadScrollData() {
        FileConfiguration config = plugin.getConfig();

        if (config.contains("ticket")) {
            String materialName = config.getString("ticket.material");
            Material material = Material.getMaterial(materialName);
            if (material == null) {
                material = Material.PAPER;
            }

            String displayName = ColorCode.colorCodes(config.getString("ticket.display-name"));
            List<String> lore = config.getStringList("ticket.lore");
            lore.replaceAll(ColorCode::colorCodes);

            int customModelData = config.getInt("ticket.custom-model-data", 0);

            ItemStack ticketItem = new ItemStack(material);
            ItemMeta meta = ticketItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(displayName);
                meta.setLore(lore);
                ticketItem.setItemMeta(meta);
            }

            this.ticket = ticketItem;
        }

        if (config.contains("location")) {
            location = config.getLocation("location");
        }

        cooldown = config.getInt("cooldown.cooltime", 5);
    }

    public ItemStack getTicket() {
        return ticket;
    }

    public Location getLocation() {
        return location;
    }

    public void setTicketLocation(Location location) {
        this.location = location;
        plugin.getConfig().set("location", location);
        plugin.saveConfig();
    }

    public int getCooldown() {
        return cooldown;
    }
}
