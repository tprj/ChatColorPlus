package xyz.tprj.chatcolorplus.listener;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class PlayerChatListener implements Listener {

    private final boolean isPAPIEnabled;

    public PlayerChatListener(boolean isPAPIEnabled) {
        this.isPAPIEnabled = isPAPIEnabled;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (isPAPIEnabled) {
            event.setMessage(ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(event.getPlayer(),event.getMessage()))));
        } else {
            event.setMessage(ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', event.getMessage())));
        }
    }
}
