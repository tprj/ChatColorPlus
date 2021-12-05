package xyz.tprj.chatcolorplus.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.tprj.chatcolorplus.manager.NickNameManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NickNameCommand implements CommandExecutor, TabCompleter {

    private NickNameManager nickNameManager;

    public NickNameCommand(NickNameManager nickNameManager) {
        this.nickNameManager = nickNameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0 && args[0].equals("reset")) {
            if (!(sender instanceof Player)) return false;
            nickNameManager.onChangeNick((Player) sender,null);
        } else {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[" + ChatColor.DARK_AQUA + "NICK" + ChatColor.GREEN + "] " + ChatColor.WHITE + "\"/nick <new name>\" change your nick name | \"/nick reset\" to reset your nick name");
                return true;
            }
            if (sender.hasPermission("chatcolorplus.nickadmin") && Bukkit.getPlayer(args[0]) != null && args.length >= 2) {
                Player p = Bukkit.getPlayer(args[0]);
                if (args[1].equals("reset")) {
                    nickNameManager.onChangeNick(p,null);
                } else {
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(args));
                    list.remove(0);
                    String newName = String.join(" ",list);
                    nickNameManager.onChangeNick(p, newName);
                }
                sender.sendMessage(ChatColor.GREEN + "[" + ChatColor.DARK_AQUA + "NICK" + ChatColor.GREEN + "] " + ChatColor.WHITE + "Your nickname has been changed by Admin");
                return true;
            }
            if (!(sender instanceof Player)) return false;
            if (!sender.hasPermission("chatcolorplus.nick")) return false;
            nickNameManager.onChangeNick((Player) sender,String.join(" ",args));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("nick")) {
            if (sender.hasPermission("chatcolorplus.nickadmin")) {
                if (args.length == 1) {
                    List<String> ps = new ArrayList<>();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.getName().startsWith(args[0]) || ChatColor.stripColor(onlinePlayer.getDisplayName()).startsWith(args[0])) {
                            ps.add(onlinePlayer.getName());
                        }
                    }
                    return ps;
                }
            }
        }
        return null;
    }
}
