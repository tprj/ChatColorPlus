package xyz.tprj.chatcolorplus.wrapper;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class V1_17_R1 extends ChatColorPlusWrapper {
    @Override
    public ItemStack setItemColor(ItemStack item, String hexColor, String name) {
        net.minecraft.world.item.ItemStack craftItemStack = CraftItemStack.asNMSCopy(item);
        net.minecraft.nbt.NBTTagCompound compound = (craftItemStack.hasTag()) ? craftItemStack.getTag() : new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound temp = new net.minecraft.nbt.NBTTagCompound();
        temp.setString("Name", String.format("{\"color\":\"#%s\",\"text\":\"%s\"}", hexColor, name));
        compound.set("display", temp);
        craftItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(craftItemStack);
    }

    @Override
    public ItemStack resetItemColor(ItemStack item) {
        net.minecraft.world.item.ItemStack craftItemStack = CraftItemStack.asNMSCopy(item);
        net.minecraft.nbt.NBTTagCompound compound = (craftItemStack.hasTag()) ? craftItemStack.getTag() : new net.minecraft.nbt.NBTTagCompound();
        net.minecraft.nbt.NBTTagCompound temp = new net.minecraft.nbt.NBTTagCompound();
        temp.setString("Name", String.format("{\"text\":\"%s\"}", stripColor(item.hasItemMeta() ? item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : null : null)));
        compound.set("display", temp);
        craftItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(craftItemStack);
    }
}