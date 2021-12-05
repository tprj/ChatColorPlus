package xyz.tprj.chatcolorplus.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class CommandListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        /*if (event.getMessage().startsWith("/tell") ||
                event.getMessage().startsWith("/minecraft:tell") ||
                event.getMessage().startsWith("/whisper") ||
        event.getMessage().startsWith("/minecraft:whisper") ||
        event.getMessage().startsWith("/w") ||
        event.getMessage().startsWith("/minecraft:w")) {
            String[] commandLine = event.getMessage().split(" ");
            if (commandLine.length > 2) {
                if (!event.getMessage().endsWith("§x§a§b§c§d§1§4§r")) {
                    event.setCancelled(true);
                    event.getPlayer().chat(ChatColorPlus.translateChatColorPlus(event.getMessage()) + "§x§a§b§c§d§1§4§r");
                }
            }
        } else {*/
            event.setMessage(ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(), event.getMessage()));
        //}
    }


    @EventHandler(ignoreCancelled = true)
    public void onServerCommand(ServerCommandEvent event) {
        event.setCommand(ChatColorPlus.translateChatColorPlus(event.getCommand()));
    }
}
