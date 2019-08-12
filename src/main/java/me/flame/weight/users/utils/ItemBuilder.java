package me.flame.weight.users.utils;

import me.flame.weight.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    ItemStack itemStack;

    public ItemBuilder(Material m, int amount) {
        itemStack = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, byte number) {
        itemStack = new ItemStack(m, amount, number);
    }

    public ItemBuilder setDisplayName(String itemName) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatUtils.format(itemName));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... itemLore) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList();

        for (String lores : itemLore) {
            lore.add(ChatUtils.format(lores));
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}