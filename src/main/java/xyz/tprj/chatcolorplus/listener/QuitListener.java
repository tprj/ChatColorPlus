package xyz.tprj.chatcolorplus.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class QuitListener implements Listener {

    private boolean isQuitMessageEnable;
    private String quitMessage;

    boolean pAPIEnable;

    public QuitListener(boolean quitMessageEnable,String quitMessage) {
        this.isQuitMessageEnable = quitMessageEnable;
        this.quitMessage = ChatColorPlus.translateChatColorPlus(ChatColor.translateAlternateColorCodes('&',quitMessage));
        this.pAPIEnable = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (isQuitMessageEnable) {
            if (pAPIEnable) {
                event.setQuitMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(),quitMessage.replaceAll("%Player%",event.getPlayer().getDisplayName())));
            } else {
                event.setQuitMessage(quitMessage.replaceAll("%Player%", event.getPlayer().getDisplayName()));
            }
        }
    }

    public void reload(boolean messageEnable, String quitMessage) {
        this.isQuitMessageEnable = messageEnable;
        this.quitMessage = ChatColorPlus.translateChatColorPlus(ChatColor.translateAlternateColorCodes('&', quitMessage));
        this.pAPIEnable = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
