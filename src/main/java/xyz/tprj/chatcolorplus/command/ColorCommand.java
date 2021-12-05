package xyz.tprj.chatcolorplus.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tprj.chatcolorplus.ChatColorPlus;

public class ColorCommand implements CommandExecutor {

    private ChatColorPlus plugin;

    public ColorCommand(ChatColorPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length != 0 && args[0].equals("reload")) {
                if (sender.hasPermission("chatcolorplus.reload")) {
                    plugin.reload();
                    sender.sendMessage(ChatColor.GREEN + "Success to reload the config file");
                }
                return true;
            }
            return false;
        }
        if (sender.hasPermission("chatcolorplus.color")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColorPlus.translateChatColorPlus("%Aqua%[%Bold%CC+%Reset%%Aqua%] %Red%There is no args./color <New Color Code>"));
                return true;
            }
            if (args[0].equals("reset")) {
                ((Player) sender).getInventory().setItemInMainHand(plugin.resetItemColor(((Player) sender).getInventory().getItemInMainHand()));
                return true;
            }
            if (args[0].equals("reload")) {
                if (sender.hasPermission("chatcolorplus.reload")) {
                    plugin.reload();
                    sender.sendMessage(ChatColorPlus.translateChatColorPlus("%Aqua%[%Bold%CC+%Reset%%Aqua%] %Red%Config file has reloaded"));
                }
                return true;
            }
            ItemStack item = ((Player) sender).getInventory().getItemInMainHand();
            if (item.getType() != Material.AIR) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName()) {
                    if (args[0].startsWith("#")) {
                        ((Player) sender).getInventory().setItemInMainHand(plugin.setItemColor(item, args[0].substring(1)));
                    } else {
                        String arg0 = args[0];
                        if (arg0.startsWith("§x")) {
                            String co = arg0.substring(2).replaceAll("§","");
                            ((Player) sender).getInventory().setItemInMainHand(plugin.setItemColor(item,co));
                            return true;
                        }
                        ((Player) sender).getInventory().setItemInMainHand(plugin.setItemColor(item, args[0]));
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "This item does not have an item name.");
                }
            }
            return true;
        } else {
            sender.sendMessage("You don't have permission");
            return true;
        }
    }
}
