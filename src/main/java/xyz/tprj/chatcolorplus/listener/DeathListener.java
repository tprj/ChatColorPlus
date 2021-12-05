package xyz.tprj.chatcolorplus.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(event.getDeathMessage().replace(event.getEntity().getName(),event.getEntity().getDisplayName() + ChatColor.RESET));
    }
}
