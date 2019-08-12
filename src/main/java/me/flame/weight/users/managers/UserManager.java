package me.flame.weight.users.managers;

import me.flame.weight.Core;
import me.flame.weight.sql.SQLManager;
import me.flame.weight.users.User;
import me.flame.weight.users.listeners.XpChangeListener;
import me.flame.weight.users.managers.interfaces.IUser;
import me.flame.weight.utils.ChatUtils;
import me.flame.weight.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager implements IUser {

    private static UserManager instance = new UserManager();
    public static List<User> userList = new ArrayList<>();
    final private XpChangeListener xpChangeListener = new XpChangeListener();

    /**
     * This is creating an user in the configuration file
     * and is loading him in after the user has been created.
     *
     * @param uuid player UUID
     */

    @Override
    public void registerUser(UUID uuid) {
        if (SQLManager.driver.contains("YML")) {
            if (FileManager.get("players.yml").contains("users." + uuid) == false) {
                try {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                    FileManager.set("players.yml", "users." + uuid + ".name", p.getName());
                    FileManager.set("players.yml", "users." + uuid + ".weight", 65);
                    FileManager.set("players.yml", "users." + uuid + ".xp", 0);
                    FileManager.save(Core.getInstance(), "players.yml");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    loadUser(uuid);
                }

            } else {
                loadUser(uuid);
            }
        } else if (SQLManager.driver.contains("MySQL")) {
            if (FileManager.get("players.yml").contains("users." + uuid)) {
                try (Connection connection = Core.getInstance().hikari.getConnection()) {
                    OfflinePlayer p = Bukkit.getPlayer(uuid);
                    PreparedStatement insert = connection.prepareStatement("SELECT * FROM `playerData` WHERE UUID = '" + uuid + "';");
                    ResultSet result = insert.executeQuery();

                    if (!result.next()) {
                        insert.executeUpdate("INSERT INTO `playerData` (`UUID`, `name`, `weight`, `xp`) VALUE ('" + uuid + "','" + p.getName() + "','" + FileManager.get("config.yml").getInt("users." + uuid + ".weight" + "', '" + FileManager.get("config.yml").getInt("users." + uuid + ".xp" + "');")));
                    }

                    System.out.println(ChatUtils.format("&c" + p.getName() + " &7aangemaakt!"));
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    loadUser(uuid);
                }
            } else {
                try (Connection connection = Core.getInstance().hikari.getConnection()) {
                    OfflinePlayer p = Bukkit.getPlayer(uuid);
                    PreparedStatement insert = connection.prepareStatement("SELECT * FROM `playerData` WHERE UUID = '" + uuid + "';");
                    ResultSet result = insert.executeQuery();

                    if (!result.next()) {
                        insert.executeUpdate("INSERT INTO `playerData` (`UUID`, `name`, `weight`, `xp`) VALUE ('" + uuid + "','" + p.getName() + "','65', '0');");
                    }

                    System.out.println(ChatUtils.format("&c" + p.getName() + " &7aangemaakt! (NEW)"));
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    loadUser(uuid);
                }
            }
        }
    }


    /**
     * This is loading the player in if he exists in the configuration file.
     * If he doens't exist. I will use the {RegisterUser} to register him.
     *
     * @param uuid player UUID
     */

    @Override
    public void loadUser(UUID uuid) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
        if (SQLManager.driver.contains("YML")) {
            if (FileManager.get("players.yml").contains("users." + uuid)) {
                try {
                    User user;
                    String name = p.getName();
                    Integer weight = FileManager.get("players.yml").getInt("users." + uuid + ".weight");
                    Integer xp = FileManager.get("players.yml").getInt("users." + uuid + ".xp");
                    user = new User(name, uuid, weight, xp);

                    userList.add(user);

                    System.out.println(ChatUtils.format("&c" + p.getName() + " &7geladen!"));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                registerUser(uuid);
            }
        } else if (SQLManager.driver.contains("MySQL")) {
            try {
                Connection connection = Core.getInstance().hikari.getConnection();
                PreparedStatement select = connection.prepareStatement("SELECT * FROM `playerData` WHERE UUID = '" + uuid + "';");
                ResultSet result = select.executeQuery();
                if (result.next()) {
                    User user;
                    String name = p.getName();
                    Integer weight = result.getInt("weight");
                    Integer xp = result.getInt("xp");

                    user = new User(name, uuid, weight, xp);
                    userList.add(user);

                    System.out.println("SQL Geladen");
                } else {
                    registerUser(uuid);
                }

                connection.close();
                select.close();
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This will save the player, after the player is saved it will reload the player
     * To pull the right information from the Arraylist.
     *
     * @param uuid player UUID
     */

    @Override
    public void saveUser(UUID uuid) {
        if (FileManager.get("players.yml").contains("users." + uuid)) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            for (User user : userList) {
                if (user.getUuid() == uuid) {
                    try {
                        FileManager.set("players.yml", "users." + uuid + ".name", p.getName());
                        FileManager.set("players.yml", "users." + uuid + ".weight", user.getWeight());
                        FileManager.set("players.yml", "users." + uuid + ".xp", user.getXp());

                        FileManager.save(Core.getInstance(), "players.yml");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } finally {
                        if (Bukkit.getPlayer(uuid).isOnline()) {
                            reloadUser(uuid);
                        }
                        break;
                    }
                }
            }
        } else {
            registerUser(uuid);
        }
    }

    /**
     * Reloading the user, removing it from the list and after that loading it back in.
     *
     * @param uuid player UUID
     */

    @Override
    public void reloadUser(UUID uuid) {
        for (User user : userList) {
            if (user.getUuid() == uuid) {
                try {
                    userList.remove(user);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    if (!userList.contains(user)) {
                        loadUser(uuid);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Pulling players weight
     *
     * @param uuid player UUID
     * @return the player weight
     */

    @Override
    public Integer getWeight(UUID uuid) {
        for (User user : userList) {
            if (user.getUuid() == uuid) {
                return user.getWeight();
            }
        }
        return 0;
    }

    /**
     * Pulling the players XP
     *
     * @param uuid player UUID
     * @return the player XP
     */

    @Override
    public Integer getXP(UUID uuid) {
        for (User user : userList) {
            if (user.getUuid() == uuid) {
                return user.getXp();
            }
        }
        return 0;
    }

    /**
     * Changing the players weight to a wanted weight.
     *
     * @param uuid   players UUID
     * @param weight the new weight of the player
     */

    @Override
    public void setWeight(UUID uuid, Integer weight) {
        for (User user : userList) {
            if (user.getUuid() == uuid) {
                if (SQLManager.driver.contains("YML")) {
                    try {
                        FileManager.set("players.yml", "users." + uuid + ".weight", weight);
                        FileManager.save(Core.getInstance(), "players.yml");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } finally {
                        reloadUser(uuid);
                        break;
                    }
                } else if (SQLManager.driver.contains("MySQL")) {
                    try {
                        Connection connection = Core.getInstance().hikari.getConnection();
                        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `playerData` WHERE UUID = '" + uuid + "';");

                        statement.executeUpdate("UPDATE `playerData` set `weight` = '" + weight + "' WHERE UUID = '" + uuid + "';");

                        connection.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        reloadUser(uuid);
                        break;
                    }
                }
            }
        }
    }


    /**
     * Chaning the players XP to the wanted amount of XP!
     *
     * @param uuid players UUID
     * @param xp   the new XP of the player
     */

    @Override
    public void setXP(UUID uuid, Integer xp) {
        for (User user : userList) {
            if (user.getUuid() == uuid) {
                if (SQLManager.driver.contains("YML")) {
                    try {
                        FileManager.set("players.yml", "users." + uuid + ".xp", xp);
                        FileManager.save(Core.getInstance(), "players.yml");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } finally {
                        reloadUser(uuid);
                    }
                } else if (SQLManager.driver.contains("MySQL")) {
                    try {
                        Connection connection = Core.getInstance().hikari.getConnection();
                        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `playerData` WHERE UUID = '" + uuid + "';");

                        statement.executeUpdate("UPDATE `playerData` set `xp` = '" + xp + "' WHERE UUID = '" + uuid + "';");

                        connection.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        reloadUser(uuid);
                        break;
                    }
                }
            }
        }
        if (getXP(uuid) >= 1000) {
            xpChangeListener.WeightUp(uuid);
        } else if (getXP(uuid) <= 0) {
            xpChangeListener.WeightDown(uuid);
        }
    }

    /**
     * Returning a static instance of this class.
     *
     * @return instance
     */

    public static UserManager getInstance() {
        return instance;
    }
}
