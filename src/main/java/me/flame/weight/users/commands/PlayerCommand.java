package me.flame.weight.users.commands;

import me.flame.weight.users.commands.base.CommandBase;
import me.flame.weight.users.managers.UserManager;
import me.flame.weight.users.utils.inventorys.PlayerStatsInventory;
import me.flame.weight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PlayerCommand extends CommandBase {

    final private PlayerStatsInventory playerStatsInventory = new PlayerStatsInventory();

    public PlayerCommand() {
        super("player", "The commands to edit an player", "", 0, 3);
    }

    @Override
    public void run(CommandSender sender, String[] args) throws NotImplementedException {
        if (args.length == 0) {
            sender.sendMessage(ChatUtils.format("&8(&b!&8) &7Command usages:"));
            sender.sendMessage(ChatUtils.format("&8* &7/weight player <name>"));
            sender.sendMessage(ChatUtils.format("&8* &7/weight player <name> setweight <amount>"));
            sender.sendMessage(ChatUtils.format("&8* &7/weight player <name> setxp <amount>"));
            return;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtils.format("&8* &7Om bugs te voorkomen kun je enkels spelers bekijken die online zijn."));
            sender.sendMessage(ChatUtils.format("&8* &7Deze speler is niet online! Probeer een andere."));
            return;
        }
        if (args.length == 1) {
            if(!(sender instanceof Player)) return;
            Player p = (Player) sender;
            sender.sendMessage(ChatUtils.format("&8* &7Alle gegevens van &9" + target.getName() + " &7worden geladen!"));
            playerStatsInventory.playerStats(p, target.getUniqueId());
            return;
        }

        switch (args[1].toLowerCase()) {
            case "setweight":
            case "setw":
                if (args.length == 2) {
                    sender.sendMessage(ChatUtils.format("&8(&b!&8) &7Command usage:"));
                    sender.sendMessage(ChatUtils.format("&8* &7/weight player <name> setweight <amount>"));
                    break;
                }
                if (ChatUtils.isInt(args[2])) {
                    Integer weight = Integer.valueOf(args[2]);
                    UserManager.getInstance().setWeight(target.getUniqueId(), weight);

                    sender.sendMessage(ChatUtils.format("&8* &7Je hebt het gewicht van &9" + target.getName() + " &7verandert naar: &9" + UserManager.getInstance().getWeight(target.getUniqueId())));
                    break;
                } else {
                    sender.sendMessage(ChatUtils.format("&8* &7De hoeveelheid mag enkel nummers bevatten!"));
                    break;
                }
            case "setxp":
                if (args.length == 2) {
                    sender.sendMessage(ChatUtils.format("&8(&b!&8) &7Command usage:"));
                    sender.sendMessage(ChatUtils.format("&8* &7/weight player <name> setxp <amount>"));
                    break;
                }
                if (ChatUtils.isInt(args[2])) {
                    Integer amount = Integer.valueOf(args[2]);
                    UserManager.getInstance().setXP(target.getUniqueId(), amount);

                    sender.sendMessage(ChatUtils.format("&8* &7Je hebt het XP van &9" + target.getName() + " &7verandert naar: &9" + amount + "/1000"));
                    break;
                } else {
                    sender.sendMessage(ChatUtils.format("&8* &7De hoeveelheid mag enkel nummers bevatten!"));
                    break;
                }
            default:
                sender.sendMessage(ChatUtils.format("&8(&b!&8) &7Command usages:"));
                sender.sendMessage(ChatUtils.format("&8* &7/weight player <name>"));
                sender.sendMessage(ChatUtils.format("&8* &7/weight player <name> setweight <amount>"));
                sender.sendMessage(ChatUtils.format("&8* &7/weight player <name> setxp <amount>"));
        }
    }
}
