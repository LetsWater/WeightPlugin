package me.flame.weight.users.listeners;

import me.flame.weight.users.managers.UserManager;
import me.flame.weight.users.managers.interfaces.IXP;

import java.util.UUID;

public class XpChangeListener implements IXP {

    /**
     * Checking if the players XP is above or equal to 100
     * If so it will set the players weight + 1
     *
     * Using: {@link UserManager}
     *
     * @param uuid player UUID
     */

    @Override
    public void WeightUp(UUID uuid) {
        if (UserManager.getInstance().getXP(uuid) >= 1000) {
            try {
                UserManager.getInstance().setWeight(uuid, UserManager.getInstance().getWeight(uuid) + 1);
                UserManager.getInstance().setXP(uuid, 1);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } finally {
                UserManager.getInstance().reloadUser(uuid);
            }
        }
    }

    /**
     * Checking if the players XP is below or equal to 0
     * If so it will set the players weight - 1
     *
     * Using: {@link UserManager}
     *
     * @param uuid
     */

    @Override
    public void WeightDown(UUID uuid) {
        if (UserManager.getInstance().getXP(uuid) <= 0) {
            try {
                UserManager.getInstance().setWeight(uuid, UserManager.getInstance().getWeight(uuid) - 1);
                UserManager.getInstance().setXP(uuid, 999);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } finally {
                UserManager.getInstance().reloadUser(uuid);
            }
        }
    }
}
