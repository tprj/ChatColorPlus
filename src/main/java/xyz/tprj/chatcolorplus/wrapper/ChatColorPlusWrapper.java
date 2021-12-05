package xyz.tprj.chatcolorplus.wrapper;

import org.bukkit.inventory.ItemStack;

abstract public class ChatColorPlusWrapper {
    abstract public ItemStack setItemColor(ItemStack item,String hex,String name);
    abstract public ItemStack resetItemColor(ItemStack item);

    public String stripColor(String text) {
        if (text == null) return null;
        StringBuilder sb = new StringBuilder();
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '§') {
                i++;
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
