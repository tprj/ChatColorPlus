package xyz.tprj.chatcolorplus.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class PlayerBookEditListener implements Listener {
    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        BookMeta bm = event.getNewBookMeta();
        bm.setTitle(ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(),ChatColor.translateAlternateColorCodes('&', bm.getTitle() != null ? bm.getTitle() : "")));
        for (int i = 1; i <= bm.getPageCount(); i++) {
            bm.setPage(i, ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(),ChatColor.translateAlternateColorCodes('&', bm.getPage(i))));
        }
        event.setNewBookMeta(bm);
    }
}
