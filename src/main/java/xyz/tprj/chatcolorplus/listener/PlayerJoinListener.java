package xyz.tprj.chatcolorplus.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.tprj.chatcolorplus.ChatColorPlus;
import xyz.tprj.chatcolorplus.manager.NickNameManager;

public class PlayerJoinListener implements Listener {

    private NickNameManager nickNameManager;
    private boolean isMessageEnable;
    private String joinMessage;

    private boolean pAPIEnable;

    public PlayerJoinListener(NickNameManager nickNameManager, boolean messageEnable, String joinMessage) {
        this.nickNameManager = nickNameManager;
        this.isMessageEnable = messageEnable;
        this.joinMessage = ChatColorPlus.translateChatColorPlus(ChatColor.translateAlternateColorCodes('&', joinMessage));
        this.pAPIEnable = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (nickNameManager != null) {
            nickNameManager.onJoin(event.getPlayer());
        }
        if (isMessageEnable) {
            if (pAPIEnable) {
                event.setJoinMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(),joinMessage.replaceAll("%Player%", event.getPlayer().getDisplayName())));
            } else {
                event.setJoinMessage(joinMessage.replaceAll("%Player%", event.getPlayer().getDisplayName()));
            }
        }
    }

    public void reload(boolean messageEnable, String joinMessage) {
        this.isMessageEnable = messageEnable;
        this.joinMessage = ChatColorPlus.translateChatColorPlus(ChatColor.translateAlternateColorCodes('&', joinMessage));
        this.pAPIEnable = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
