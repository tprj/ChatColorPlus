package xyz.tprj.chatcolorplus;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tprj.chatcolorplus.command.ColorCommand;
import xyz.tprj.chatcolorplus.command.NickNameCommand;
import xyz.tprj.chatcolorplus.listener.*;
import xyz.tprj.chatcolorplus.manager.NickNameManager;
import xyz.tprj.chatcolorplus.papi.NickNameExpansion;
import xyz.tprj.chatcolorplus.wrapper.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;

public final class ChatColorPlus extends JavaPlugin implements Listener {

    private NickNameManager nickNameManager;

    private ChatColorPlusWrapper wrapper;

    private final static HashMap<String, String> moreColors = new HashMap<>();

    private PlayerJoinListener playerJoinListener;
    private QuitListener quitListener;

    public static String translateChatColorPlus(String stringOfTranslate) {

        if (stringOfTranslate == null) {
            return "";
        }
        String text = stringOfTranslate;
        for (String s : moreColors.keySet()) {
            text = text.replaceAll(s,moreColors.get(s));
        }
        text = text.replaceAll("%Bold%","§l");
        text = text.replaceAll("%Reset%","§r");
        String temp;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (i < text.length() - 9) {
                temp = text.substring(i, i + 9);
                if (temp.startsWith("%#") && temp.endsWith("%")) {
                    try {
                        Integer.parseInt(temp.substring(2, 8), 16);
                        sb.append("§x");
                        char[] c = temp.toCharArray();
                        for (int i1 = 2; i1 < c.length - 1; i1++) {
                            sb.append("§").append(c[i1]);
                        }
                        i += 8;
                    } catch ( NumberFormatException ignored ) {
                    }
                } else {
                    sb.append(text.charAt(i));
                }
            } else {
                sb.append(text.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String translateChatColorPlusWithPlayerPermission(Player player,String stringOfTranslate) {
        if (stringOfTranslate == null) {
            return "";
        }
        if (player == null) {
            return translateChatColorPlus(stringOfTranslate);
        }
        String text = stringOfTranslate;
        for (String s : moreColors.keySet()) {
            if (player.hasPermission("chatcolorplus.chatcolor." + s)) {
                text = text.replaceAll(s, moreColors.get(s));
            }
        }
        if (player.hasPermission("chatcolorplus.chatcolor.hex")) {
            String temp;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                if (i < text.length() - 9) {
                    temp = text.substring(i, i + 9);
                    if (temp.startsWith("%#") && temp.endsWith("%")) {
                        try {
                            Integer.parseInt(temp.substring(2, 8), 16);
                            sb.append("§x");
                            char[] c = temp.toCharArray();
                            for (int i1 = 2; i1 < c.length - 1; i1++) {
                                sb.append("§").append(c[i1]);
                            }
                            i += 8;
                        } catch ( NumberFormatException ignored ) {
                        }
                    } else {
                        sb.append(text.charAt(i));
                    }
                } else {
                    sb.append(text.charAt(i));
                }
            }
            return sb.toString();
        } else {
            return text;
        }
    }

    @Deprecated
    public static String translateToJsonFormat(String stringOfTranslate) {
        if (stringOfTranslate == null) {
            return ComponentSerializer.toString(new TextComponent(""));
        } else {
            char[] chars = stringOfTranslate.toCharArray();
            TextComponent component = new TextComponent();
            StringBuilder sb = new StringBuilder();
            int length = chars.length;
            for (int i = 0; i < length; i++) {
                if (chars[i] == '%' &&i <= length - 9 ) {

                }
            }
        }
        return "{\"text\":\"\"}";
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        Metrics metrics = new Metrics(this,8069);
        metrics.addCustomChart(new Metrics.SimplePie("server_language", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return System.getProperty("user.language");
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("version", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getServer().getVersion();
            }
        }));

        metrics.addCustomChart(new Metrics.SimplePie("bukkit_version", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getServer().getBukkitVersion();
            }
        }));

        String a = getServer().getClass().getPackage().getName();
        String version = a.substring(a.lastIndexOf('.') + 1);
        switch (version) {
            case "v1_16_R1":
                wrapper = new V1_16_R1();
                break;
            case "v1_16_R2":
                wrapper = new V1_16_R2();
                break;
            case "v1_16_R3":
                wrapper = new V1_16_R3();
                break;
            case "v1_17_R1":
                wrapper = new V1_17_R1();
                break;
            case "v1_18_R1":
                wrapper = new V1_18_R1();
                break;
            default:
                getLogger().warning("Unknown version");
                wrapper = null;
                break;
        }

        if (!getConfig().contains("nickname.enable")) {
            getConfig().set("nickname.enable",true);
            saveConfig();
        }
        boolean isNickEnable = getConfig().getBoolean("nickname.enable",true);
        if (isNickEnable) {
            nickNameManager = new NickNameManager(getDataFolder().getAbsolutePath() + "/nick.yml");
            if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                new NickNameExpansion(this).register();
            }
        } else {
            nickNameManager = null;
        }
        getServer().getPluginManager().registerEvents(new PlayerChatListener(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerBookEditListener(),this);
        getServer().getPluginManager().registerEvents(new CommandListener(),this);
        this.playerJoinListener = new PlayerJoinListener(nickNameManager,getConfig().getBoolean("joinmessage.enable",false),getConfig().getString("joinmessage.message"));
        getServer().getPluginManager().registerEvents(playerJoinListener,this);
        this.quitListener = new QuitListener(getConfig().getBoolean("quitmessage.enable",false),getConfig().getString("quitmessage.message"));
        getServer().getPluginManager().registerEvents(quitListener,this);
        getServer().getPluginManager().registerEvents(new DeathListener(),this);
        if (isNickEnable) {
            NickNameCommand nickNameCommand = new NickNameCommand(nickNameManager);
            getCommand("nick").setExecutor(nickNameCommand);
            getCommand("nick").setTabCompleter(nickNameCommand);
        }
        getCommand("color").setExecutor(new ColorCommand(this));
        loadColors();
    }

    public ItemStack setItemColor(ItemStack item,String hex) {
        //System.out.println(wrapper);
        return wrapper.setItemColor(item,hex,item.getItemMeta() != null ? item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "" : "");
    }

    public void reload() {
        reloadConfig();
        loadColors();
        playerJoinListener.reload(getConfig().getBoolean("joinmessage.enable",false),getConfig().getString("joinmessage.message"));
        quitListener.reload(getConfig().getBoolean("quitmessage.enable",false),getConfig().getString("quitmessage.message"));
    }

    public void loadColors() {
        moreColors.clear();
        moreColors.put("%Bold%","§l");
        moreColors.put("%Reset%","§r");
        moreColors.put("%Obfuscated%","§k");
        moreColors.put("%Strikethrough%","§m");
        moreColors.put("%Underline%","§n");
        moreColors.put("%Italic%","§o");
        File colorYml = new File(getDataFolder().getAbsolutePath() + "/colors.yml");
        YamlConfiguration colorConfig;
        if (!colorYml.exists()) {
            try {
                colorYml.createNewFile();
                colorConfig = YamlConfiguration.loadConfiguration(colorYml);
                colorConfig.set("White","FFFFFF");
                colorConfig.set("Silver","C0C0C0");
                colorConfig.set("Gray","808080");
                colorConfig.set("Black","000000");
                colorConfig.set("Red","F60C03");
                colorConfig.set("Maroon","800300");
                colorConfig.set("Yellow","FFFF00");
                colorConfig.set("Olive","808000");
                colorConfig.set("Lime","57FF00");
                colorConfig.set("Green","288000");
                colorConfig.set("Aqua","4EFFFF");
                colorConfig.set("Teal","23807F");
                colorConfig.set("Blue","0100FF");
                colorConfig.set("Navy","010080");
                colorConfig.set("Fuchsia","F40CFF");
                colorConfig.set("Purple","80037F");
                colorConfig.save(colorYml);
                addColor("%White%","FFFFFF");
                addColor("%Silver%","C0C0C0");
                addColor("%Gray%","808080");
                addColor("%Black%","000000");
                addColor("%Red%","F60C03");
                addColor("%Maroon%","800300");
                addColor("%Yellow%","FFFF00");
                addColor("%Olive%","808000");
                addColor("%Lime%","57FF00");
                addColor("%Green%","288000");
                addColor("%Aqua%","4EFFFF");
                addColor("%Teal%","23807F");
                addColor("%Blue%","0100FF");
                addColor("%Navy%","010080");
                addColor("%Fuchsia%","F40CFF");
                addColor("%Purple%","80037F");
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        } else {
            colorConfig = YamlConfiguration.loadConfiguration(colorYml);
            for (String key : colorConfig.getKeys(false)) {
                String v = colorConfig.getString(key);
                if (v != null) {
                    if (v.length() == 6) {
                        try {
                            Integer.parseInt(v, 16);
                            addColor(String.format("%%%s%%", key), v);
                        } catch ( Exception ignored ) {
                        }
                    } else if (v.length() == 7 && v.startsWith("#")) {
                        try {
                            Integer.parseInt(v.substring(1),16);
                            addColor(String.format("%%%s%%",key),v.substring(1));
                        } catch ( Exception ignored ) {
                        }
                    }
                }
            }
        }
    }

    public void addColor(String name,String hexCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("§x");
        for (char c : hexCode.toCharArray()) {
            sb.append("§").append(c);
        }
        moreColors.put(name,sb.toString());
        moreColors.put(name.toLowerCase(),sb.toString());
    }

    public ItemStack resetItemColor(ItemStack itemStack) {
        return wrapper.resetItemColor(itemStack);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        nickNameManager.save();
    }

    public NickNameManager getNickNameManager() {
        return nickNameManager;
    }

    public String getColor(String id) {
        return moreColors.getOrDefault("%" + id + "%",null);
    }

    public String getMoreColor(String origin) {
        return moreColors.getOrDefault(origin,null);
    }
}
