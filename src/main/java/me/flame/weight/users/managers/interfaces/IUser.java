package me.flame.weight.users.managers.interfaces;

import java.util.UUID;

public interface IUser {

    void registerUser(UUID uuid);

    void loadUser(UUID uuid);

    void saveUser(UUID uuid);

    void reloadUser(UUID uuid);

    Integer getWeight(UUID uuid);

    Integer getXP(UUID uuid);

    void setWeight(UUID uuid, Integer weight);

    void setXP(UUID uuid, Integer xp);
}
