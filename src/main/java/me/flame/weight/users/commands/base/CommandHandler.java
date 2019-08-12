package me.flame.weight.users.commands.base;

import me.flame.weight.Core;
import me.flame.weight.users.commands.AboutCommand;
import me.flame.weight.users.commands.HelpCommand;
import me.flame.weight.users.commands.PlayerCommand;
import me.flame.weight.users.commands.ReloadCommand;
import me.flame.weight.users.managers.interfaces.IHandler;
import me.flame.weight.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandHandler implements IHandler, CommandExecutor, TabCompleter {

    private static List<CommandBase> commands;
    private static CommandHandler instance = new CommandHandler();

    /**
     * @return an static instance of the {@link CommandHandler} class
     */

    public static CommandHandler getInstance() {
        return instance;
    }

    @Override
    public void enable(Core core) {

        commands = new ArrayList<>();
        core.getCommand("weight").setExecutor(this);

        Stream.of(
                new HelpCommand(), new AboutCommand(), new PlayerCommand(), new ReloadCommand()
        ).forEach(this::register);
    }

    @Override
    public void disable(Core core) {
        commands.clear();
        commands = null;
    }

    private void register(CommandBase command) {
        commands.add(command);
    }

    public List<CommandBase> getCommands() {
        return commands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("weight")) {
            return true;
        }
        if (args.length == 0 || args[0].isEmpty()) {
            sender.sendMessage(ChatUtils.format("&b&l(!) &7Use &b/help &7for the help page!"));
        } else if (args.length >= 1)
            for (CommandBase command : commands) {
                if (!command.getName().equalsIgnoreCase(args[0]) && !command.getAliases().contains(args[0].toLowerCase())) {
                    continue;
                }
                if (!sender.hasPermission(command.getPermission())) {
                    sender.sendMessage(ChatUtils.format("&b&l(!) &7You aren't permitted to use this command!"));
                    return true;
                }
                args = Arrays.copyOfRange(args, 1, args.length);
                if ((command.getMinimumArgs() != -1 && command.getMinimumArgs() > args.length)
                        || (command.getMaximumArgs() != -1 && command.getMaximumArgs() < args.length)) {
                    return true;
                }

                command.run(sender, args);
                return true;
            }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("weight")) {
            if (args.length == 1) {
                List<String> names = new ArrayList<>();
                if (args[0].equalsIgnoreCase("")) {
                    for (String name : commands.stream().map(CommandBase::getName).collect(Collectors.toList())) {
                        if (!name.startsWith(args[0].toLowerCase())) {
                            continue;
                        }
                        names.add(name);
                    }
                } else {
                    names = commands.stream().map(CommandBase::getName).collect(Collectors.toList());
                }
                Collections.sort(names);
                return names;
            }
        }
        return null;
    }
}
