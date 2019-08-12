package me.flame.weight.users;

import java.util.UUID;

public class User {

    private String name;
    private UUID uuid;
    private Integer weight;
    private Integer xp;

    public User(String name, UUID uuid, Integer weight, Integer xp){
        this.name = name;
        this.uuid = uuid;
        this.weight = weight;
        this.xp = xp;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getXp() {
        return xp;
    }
}
