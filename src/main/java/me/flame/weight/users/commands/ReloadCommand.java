package me.flame.weight.users.commands;

import me.flame.weight.Core;
import me.flame.weight.users.commands.base.CommandBase;
import me.flame.weight.utils.ChatUtils;
import me.flame.weight.utils.PluginUtils;
import org.bukkit.command.CommandSender;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ReloadCommand extends CommandBase {
    public ReloadCommand() {
        super("reload", "Reload the plugin", "weight.reload", 0, 0);
    }

    @Override
    public void run(CommandSender sender, String[] args) throws NotImplementedException {

        PluginUtils.restartPlugin(Core.getInstance());
        sender.sendMessage(ChatUtils.format("&3&l* &fJe hebt de plugin succesvol gereload!"));
    }
}
