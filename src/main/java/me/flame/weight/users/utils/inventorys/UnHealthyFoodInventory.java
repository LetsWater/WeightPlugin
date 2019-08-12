package me.flame.weight.users.utils.inventorys;

import me.flame.weight.food.Food;
import me.flame.weight.food.interfaces.FoodManager;
import me.flame.weight.users.utils.ItemBuilder;
import me.flame.weight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class UnHealthyFoodInventory {

    public Inventory unHealthyFoodMenu(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);

        Inventory unHealthyFood = Bukkit.createInventory(null, 27, ChatUtils.format("&7Display van: &cOngezond eten!"));

        for (Food food : FoodManager.unHealthyFood) {
            unHealthyFood.addItem(new ItemBuilder(food.getMaterial(), 1)
                    .setDisplayName("&3" + food.getName())
                    .setLore(""
                            , "&3➥ &fInformatie"
                            , "&7Invloed: &cNegatief"
                            , "&7Gewicht erbij: &f" + food.getXp() + " &7gram!"
                            , "&7Ingeladen: &a&l✔"
                            , "").build());
        }

        unHealthyFood.setContents(unHealthyFood.getContents());
        p.openInventory(unHealthyFood);

        return unHealthyFood;
    }
}