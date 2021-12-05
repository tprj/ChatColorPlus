package xyz.tprj.chatcolorplus.wrapper;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class V1_16_R3 extends ChatColorPlusWrapper{
    @Override
    public ItemStack setItemColor(ItemStack item, String hexColor, String name) {
        net.minecraft.server.v1_16_R3.ItemStack craftItemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (craftItemStack.hasTag()) ? craftItemStack.getTag() : new NBTTagCompound();
        NBTTagCompound temp = new NBTTagCompound();
        temp.setString("Name", String.format("{\"color\":\"#%s\",\"text\":\"%s\"}", hexColor,name));
        compound.set("display",temp);
        craftItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(craftItemStack);
    }

    @Override
    public ItemStack resetItemColor(ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack craftItemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (craftItemStack.hasTag()) ? craftItemStack.getTag() : new NBTTagCompound();
        NBTTagCompound temp = new NBTTagCompound();
        temp.setString("Name", String.format("{\"text\":\"%s\"}",stripColor(item.hasItemMeta() ? item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : null : null)));
        compound.set("display",temp);
        craftItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(craftItemStack);
    }
}
