package xyz.tprj.chatcolorplus.listener;

import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class InventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent e) {

        if (!e.isCancelled()) {
            HumanEntity ent = e.getWhoClicked();
            if (ent instanceof Player) {
                Inventory inv = e.getInventory();
                if (inv instanceof AnvilInventory) {
                    InventoryView view = e.getView();
                    int rawSlot = e.getRawSlot();
                    if (rawSlot == view.convertSlot(rawSlot) && rawSlot == 2) {
                        ItemStack item = e.getCurrentItem();
                        if (item != null) {
                            ItemMeta meta = item.getItemMeta();
                            if (meta != null && meta.hasDisplayName()) {
                                String text = meta.getDisplayName();
                            }
                        }
                    }
                }
            }
        }
    }
}
