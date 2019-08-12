package me.flame.weight.sql;

import me.flame.weight.Core;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    public static List<String> driver = new ArrayList<>();

    public void createTable(){
        try{
            Connection connection = Core.getInstance().hikari.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `playerData` (`UUID` varchar(36), `name` varchar(36), `weight` int, `xp` int)");

            Core.getInstance().getLogger().info("Connected to a database! Using SQL to store data.");
            driver.add("MySQL");

        } catch (SQLException e){
            Core.getInstance().getLogger().info("Couldn't connect to a working database!");
            Core.getInstance().getLogger().info("Using YML to store data!");
            driver.add("YML");
        }
    }
}
