package kr.dalkubi.ButterRecallTicket.message;

import kr.dalkubi.ButterRecallTicket.ButterRecallTicket;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageConfig {

    private static File messageFile;
    public static FileConfiguration messageConfig;

    public static void setup() {
        ButterRecallTicket plugin = ButterRecallTicket.getInstance();
        if (plugin == null) {
            throw new IllegalStateException("ButterRecallTicket instance is null!");
        }

        if (messageFile == null) {
            messageFile = new File(plugin.getDataFolder(), "message.yml");
        }

        if (!messageFile.exists()) {
            plugin.saveResource("message.yml", false);
        }

        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        Message.instance = new Message(messageConfig);
    }

    public static FileConfiguration getMessageConfig() {
        if (messageConfig == null) {
            throw new IllegalStateException("MessageConfig has not been initialized. Call setup() first.");
        }
        return messageConfig;
    }

    public static void reload() {
        try {
            if (messageFile == null || !messageFile.exists()) {
                setup();
            } else {
                messageConfig = YamlConfiguration.loadConfiguration(messageFile);
                Message.instance = new Message(messageConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
