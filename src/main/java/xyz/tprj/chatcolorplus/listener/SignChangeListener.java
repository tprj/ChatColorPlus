package xyz.tprj.chatcolorplus.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class SignChangeListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        for (int i = 0; i < event.getLines().length; i++) {
            if (event.getLine(i) != null) {
                event.setLine(i, ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(),event.getLine(i)));
            }
        }
    }
}
