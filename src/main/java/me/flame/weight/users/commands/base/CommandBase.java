package me.flame.weight.users.commands.base;

import org.bukkit.command.CommandSender;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandBase {

    private String name;
    private String permission;
    private String description;

    private List<String> aliases;
    private int minimumArgs;
    private int maximumArgs;

    public CommandBase(String name, String description, String permission, int minimumArgs, int maximumArgs, String... aliases) {
        this.name = name;
        this.description = description;
        this.permission = permission;

        this.aliases = aliases == null ? new ArrayList<>() : Arrays.asList(aliases);
        this.minimumArgs = minimumArgs;
        this.maximumArgs = maximumArgs;
    }

    public abstract void run(CommandSender sender, String[] args) throws NotImplementedException;

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public int getMinimumArgs() {
        return minimumArgs;
    }

    public int getMaximumArgs() {
        return maximumArgs;
    }
}
