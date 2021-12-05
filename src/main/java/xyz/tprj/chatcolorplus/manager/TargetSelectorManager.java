package xyz.tprj.chatcolorplus.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TargetSelectorManager {

    public static Set<Entity> getEntityBySelector(String selector) {
        HashSet<Entity> set = new HashSet<>();
        if (selector.startsWith("@")) {
            // @a @e @s @r
            switch (selector.substring(0,2)) {
                case "@a":
                    set.addAll(Bukkit.getOnlinePlayers());
                    if (selector.length() >= 3 && selector.charAt(2) == '[' && selector.lastIndexOf(1) == ']') {
                        String[] selectors = selector.substring(2).split(",");
                        for (String s : selectors) {

                        }
                    }
                    return set;
                case "@e":
                    return set;
                case "@s":
                    return set;
                case "@r":
                    return set;
            }
        } else {
            // Player
            Player p = Bukkit.getPlayer(selector);
            if (p != null && p.isOnline()) {
                set.add(p);
            }
            return set;
        }
        return null;
    }
}
