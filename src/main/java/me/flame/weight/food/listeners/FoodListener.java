package me.flame.weight.food.listeners;

import me.flame.weight.food.Food;
import me.flame.weight.food.interfaces.FoodManager;
import me.flame.weight.users.managers.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodListener implements Listener {

    @EventHandler
    public void eatEvent(PlayerItemConsumeEvent e) {

        Player p = e.getPlayer();

        for (Food food : FoodManager.healthyFood) {
            if (e.getItem().getType() == food.getMaterial()) {
                UserManager.getInstance().setXP(p.getUniqueId(), UserManager.getInstance().getXP(p.getUniqueId()) - food.getXp());
                break;
            }
        }

        for (Food food : FoodManager.unHealthyFood) {
            if (e.getItem().getType() == food.getMaterial()) {
                UserManager.getInstance().setXP(p.getUniqueId(), UserManager.getInstance().getXP(p.getUniqueId()) + food.getXp());
                break;
            }
        }
    }
}
