package kr.dalkubi.ButterRecallTicket;

import kr.dalkubi.ButterRecallTicket.command.RecallTicketCmd;
import kr.dalkubi.ButterRecallTicket.command.tabcomplete.RecallTicketTab;
import kr.dalkubi.ButterRecallTicket.listener.TicketClickListener;
import kr.dalkubi.ButterRecallTicket.manage.ManageTicket;
import kr.dalkubi.ButterRecallTicket.message.MessageConfig;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ButterRecallTicket extends JavaPlugin {

    private static ButterRecallTicket instance;
    private ManageTicket manageTicket;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        MessageConfig.setup();
        manageTicket = new ManageTicket(this);

        // 명령어 등록
        PluginCommand returnTicketCmd = getCommand("귀환서");
        if (returnTicketCmd != null) {
            returnTicketCmd.setExecutor(new RecallTicketCmd(this));
        }

        // tab 등록
        returnTicketCmd.setTabCompleter(new RecallTicketTab());

        // 이벤트 리스너 등록
        getServer().getPluginManager().registerEvents(new TicketClickListener(this), this);
    }

    public void reloadMessageConfig() {
        MessageConfig.reload();
    }

    public ManageTicket getManageTicket() {
        return manageTicket;
    }

    public static ButterRecallTicket getInstance() {
        return instance;
    }
}
