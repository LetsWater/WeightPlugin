package me.flame.weight.users.utils.inventorys;

import me.flame.weight.food.Food;
import me.flame.weight.food.interfaces.FoodManager;
import me.flame.weight.users.utils.ItemBuilder;
import me.flame.weight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class HealthyFoodInventory {

    public Inventory healthyFoodMenu(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);

        Inventory healthyFood = Bukkit.createInventory(null, 27, ChatUtils.format("&7Display van: &aGezond eten!"));

        for (Food food : FoodManager.healthyFood) {
            healthyFood.addItem(new ItemBuilder(food.getMaterial(), 1)
                    .setDisplayName("&3" + food.getName())
                    .setLore(""
                            , "&3➥ &fInformatie"
                            , "&7Invloed: &aPositief"
                            , "&7Gewicht verlies: &f" + food.getXp() + " &7gram!"
                            , "&7Ingeladen: &a&l✔"
                            , "").build());
        }

        healthyFood.setContents(healthyFood.getContents());
        p.openInventory(healthyFood);

        return healthyFood;
    }
}
