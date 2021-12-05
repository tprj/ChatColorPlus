package xyz.tprj.chatcolorplus.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class PlayerInteractAtEntityListener implements Listener {

    private ChatColorPlus plugin;

    public PlayerInteractAtEntityListener(ChatColorPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        EntityType entityType = event.getRightClicked().getType();
        if (entityType == EntityType.PLAYER || entityType == EntityType.MINECART || entityType == EntityType.MINECART_CHEST || entityType == EntityType.MINECART_COMMAND || entityType == EntityType.MINECART_FURNACE || entityType == EntityType.MINECART_HOPPER || entityType == EntityType.MINECART_MOB_SPAWNER || entityType == EntityType.MINECART_TNT)
            return;
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.NAME_TAG) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            if (item.getItemMeta().hasDisplayName()) {
                event.setCancelled(true);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    event.getRightClicked().setCustomName(ChatColorPlus.translateChatColorPlusWithPlayerPermission(event.getPlayer(),item.getItemMeta().getDisplayName()));
                }, 1);
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount());
                }
            }
        }
    }
}
