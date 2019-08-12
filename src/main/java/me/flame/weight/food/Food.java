package me.flame.weight.food;

import org.bukkit.Material;

public class Food {

    private String name;
    private Material material;
    private Integer xp;
    private boolean healthy;

    public Food(String name, Material material, Integer xp, boolean healthy){

        this.name = name;
        this.material = material;
        this.xp = xp;
        this.healthy = healthy;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getXp() {
        return xp;
    }

    public boolean isHealthy() {
        return healthy;
    }
}
