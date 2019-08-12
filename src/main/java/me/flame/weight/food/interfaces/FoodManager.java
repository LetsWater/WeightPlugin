package me.flame.weight.food.interfaces;

import me.flame.weight.food.Food;
import me.flame.weight.utils.FileManager;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class FoodManager implements FoodInterface {

    private static FoodManager instance = new FoodManager();
    public static List<Food> healthyFood = new ArrayList<>();
    public static List<Food> unHealthyFood = new ArrayList<>();

    /**
     * Loading all "Healthy" food from the configuration file
     * And putting them in the ArrayList healthyFood
     */

    @Override
    public void loadHealthyFood() {
        for (String key : FileManager.get("food.yml").getConfigurationSection("food").getKeys(false)) {
            Boolean healthy = FileManager.get("food.yml").getBoolean("food." + key + ".healthy");
            if (healthy) {
                try {
                    Food food;
                    String name = key;
                    Material material = Material.getMaterial(FileManager.get("food.yml").getString("food." + key + ".material").toUpperCase());
                    if(Material.matchMaterial(FileManager.get("food.yml").getString("food." + key + ".material")) == null){
                        name = "&cFailed to load " + key;
                        material = Material.BARRIER;
                    }
                    Integer xp = FileManager.get("food.yml").getInt("food." + key + ".xp");
                    food = new Food(name, material, xp, healthy);
                    healthyFood.add(food);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loading all "UnHealthy" food from the configuration file
     * And putting them in the ArrayList unHealthyFood
     */

    @Override
    public void loadUnhealthyFood() {
        for (String key : FileManager.get("food.yml").getConfigurationSection("food").getKeys(false)) {
            Boolean healthy = FileManager.get("food.yml").getBoolean("food." + key + ".healthy");
            if (healthy == false) {
                try {
                    Food food;
                    String name = key;
                    Material material = Material.getMaterial(FileManager.get("food.yml").getString("food." + key + ".material"));
                    if(Material.matchMaterial(FileManager.get("food.yml").getString("food." + key + ".material")) == null){
                        name = "&cFailed to load " + key;
                        material = Material.BARRIER;
                    }
                    Integer xp = FileManager.get("food.yml").getInt("food." + key + ".xp");
                    food = new Food(name, material, xp, healthy);
                    unHealthyFood.add(food);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reloading both of the food category's.
     * Usefull for reloading the plugin
     */

    @Override
    public void reloadFood(){
        unHealthyFood.clear();
        healthyFood.clear();

        loadHealthyFood();
        loadUnhealthyFood();
    }

    /**
     * Returning a static instance of this class
     *
     * @return instance
     */

    public static FoodManager getInstance() {
        return instance;
    }
}
