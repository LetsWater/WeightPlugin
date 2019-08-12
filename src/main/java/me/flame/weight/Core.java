package me.flame.weight;

import com.zaxxer.hikari.HikariDataSource;
import me.flame.weight.food.interfaces.FoodManager;
import me.flame.weight.food.listeners.FoodListener;
import me.flame.weight.sql.SQLManager;
import me.flame.weight.users.commands.base.CommandHandler;
import me.flame.weight.users.listeners.PlayerListener;
import me.flame.weight.users.managers.UserManager;
import me.flame.weight.users.utils.inventorys.PlayerStatsInventory;
import me.flame.weight.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Core extends JavaPlugin implements Listener {

    public static HikariDataSource hikari;
    final private SQLManager sqlManager = new SQLManager();

    private static Core instance;
    final private FoodManager foodManager = new FoodManager();

    private CommandHandler commandHandler;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Plugin has been enabled succesfully!");

        loadConfig();
        loadListeners();

        commandHandler = new CommandHandler();
        commandHandler.enable(this);

        foodManager.loadHealthyFood();
        foodManager.loadUnhealthyFood();
        getLogger().info("I have loaded "
                + foodManager.healthyFood.size() + " healhty food and I have loaded "
                + foodManager.unHealthyFood.size() + " unHealthy food!");

        if (FileManager.get("config.yml").getString("sql.driver").equalsIgnoreCase("MySQL")) {
            connect();
            sqlManager.createTable();
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            UUID uuid = online.getUniqueId();
            UserManager.getInstance().loadUser(uuid);
        }

    }

    @Override
    public void onDisable() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            UUID uuid = online.getUniqueId();
            UserManager.getInstance().saveUser(uuid);
        }

        commandHandler.disable(this);
        UserManager.userList.clear();
        FoodManager.getInstance().healthyFood.clear();
        FoodManager.getInstance().unHealthyFood.clear();
        instance = null;
        FileManager.configs.clear();

        if(hikari != null){
            hikari.close();
        }
    }

    private void loadConfig() {
        FileManager.load(this, "config.yml");
        FileManager.load(this, "players.yml");
        FileManager.load(this, "food.yml");
    }

    private void loadListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new FoodListener(), this);
        pm.registerEvents(new PlayerStatsInventory(), this);
    }

    public void connect() {
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");

        String address = FileManager.get("config.yml").getString("sql.address");
        String[] addressSplit = address.split(":");

        hikari.addDataSourceProperty("serverName", addressSplit[0]);
        hikari.addDataSourceProperty("port", addressSplit[1]);
        hikari.addDataSourceProperty("databaseName", FileManager.get("config.yml").getString("sql.database"));
        hikari.addDataSourceProperty("user", FileManager.get("config.yml").getString("sql.username"));
        hikari.addDataSourceProperty("password", FileManager.get("config.yml").getString("sql.password"));
    }
}
