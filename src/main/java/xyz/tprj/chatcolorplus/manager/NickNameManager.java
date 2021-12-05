package xyz.tprj.chatcolorplus.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.tprj.chatcolorplus.ChatColorPlus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NickNameManager {

    private File config;
    private YamlConfiguration yamlConfiguration;

    private HashMap<String, String> names = new HashMap<>();

    public NickNameManager(String configPath) {
        this.config = new File(configPath);
        config.getParentFile().mkdirs();
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(config);
        load();
    }

    public void load() {
        for (String key : yamlConfiguration.getKeys(false)) {
            names.put(key, yamlConfiguration.getString(key));
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String name = ChatColorPlus.translateChatColorPlusWithPlayerPermission(onlinePlayer, ChatColor.translateAlternateColorCodes('&', names.get(onlinePlayer.getUniqueId().toString())));
            onlinePlayer.setDisplayName(name);
            onlinePlayer.setPlayerListName(name);
        }
    }

    public String getNickName(Player player) {
        return names.get(player.getUniqueId().toString());
    }

    public void onJoin(Player player) {
        if (!names.containsKey(player.getUniqueId().toString())) {
            names.put(player.getUniqueId().toString(), player.getName());
        } else {
            player.setDisplayName(names.get(player.getUniqueId().toString()));
            player.setPlayerListName(names.get(player.getUniqueId().toString()));
        }
    }

    public void onChangeNick(Player player, String newName) {
        if (newName == null) {
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            names.replace(player.getUniqueId().toString(), player.getName());
            player.setCustomName(player.getName());
            return;
        }
        String name = ChatColorPlus.translateChatColorPlusWithPlayerPermission(player, ChatColor.translateAlternateColorCodes('&', newName)) + ChatColor.RESET;
        if (name.endsWith(" ")) {
            name = name.substring(0,name.length()-1);
        }
        player.setCustomName(name);
        player.setDisplayName(name);
        player.setPlayerListName(name);
        names.replace(player.getUniqueId().toString(), name);
    }

    public boolean onChangeAnotherPlayersNickName(Player sender, String name, String newName) {
        String uuid = null;
        Player p = Bukkit.getPlayer(name);
        if (p == null) {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                if (offlinePlayer.getName().equals(name)) {
                    uuid = offlinePlayer.getUniqueId().toString();
                }
            }
            if (uuid != null && names.containsKey(uuid)) {
                if (newName == null) {
                    names.replace(uuid, name);
                } else {
                    names.replace(uuid, ChatColorPlus.translateChatColorPlusWithPlayerPermission(sender, ChatColor.translateAlternateColorCodes('&', newName)) + ChatColor.RESET);
                }
                return true;
            } else {
                return false;
            }
        } else {
            if (newName == null) {
                p.setDisplayName(p.getName());
                p.setPlayerListName(p.getName());
                names.replace(p.getUniqueId().toString(), p.getName());
            } else {
                String newNameAfter = ChatColorPlus.translateChatColorPlusWithPlayerPermission(sender, ChatColor.translateAlternateColorCodes('&', newName)) + ChatColor.RESET;
                names.replace(p.getUniqueId().toString(), newNameAfter);
                p.setDisplayName(newNameAfter);
                p.setPlayerListName(newNameAfter);
            }
            return true;
        }
    }

    public void save() {
        names.forEach((uuid, s) -> {
            yamlConfiguration.set(uuid, s);
        });
        try {
            yamlConfiguration.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
