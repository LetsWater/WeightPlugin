package me.flame.weight.users.commands;

import me.flame.weight.users.commands.base.CommandBase;
import me.flame.weight.users.commands.base.CommandHandler;
import me.flame.weight.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class HelpCommand extends CommandBase {

    public HelpCommand() {
        super("help", "Get a list of all the commands for this plugin", "", 0, 0);
    }

    @Override
    public void run(CommandSender sender, String[] args) throws NotImplementedException {

        sender.sendMessage(ChatUtils.format("              &8[&3Weight Plugin&8]"));
        sender.sendMessage("");
        sender.sendMessage("");
        for (CommandBase commands : CommandHandler.getInstance().getCommands()) {
            sender.sendMessage(ChatUtils.format(" &3&l* &b/weight " + commands.getName() + "&8 - &7" + commands.getDescription()));
        }

        sender.sendMessage("");
        sender.sendMessage(ChatUtils.format("              &8[&3Weight Plugin&8]"));

    }
}
