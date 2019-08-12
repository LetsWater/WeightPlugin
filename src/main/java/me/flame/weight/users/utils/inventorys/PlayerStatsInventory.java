package me.flame.weight.users.utils.inventorys;

import me.flame.weight.users.User;
import me.flame.weight.users.managers.UserManager;
import me.flame.weight.users.utils.ItemBuilder;
import me.flame.weight.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class PlayerStatsInventory implements Listener {

    final private HealthyFoodInventory healthyFoodInventory = new HealthyFoodInventory();
    final private UnHealthyFoodInventory unHealthyFoodInventory = new UnHealthyFoodInventory();

    public Inventory playerStats(Player commandExecutor, UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

        Inventory pStats = Bukkit.createInventory(null, 9, ChatUtils.format("&3Statistieken van: &f" + player.getName()));

        for (User user : UserManager.userList) {
            if (user.getUuid() == uuid) {
                pStats.setItem(4, new ItemBuilder(Material.GOLD_NUGGET, 1)
                        .setDisplayName("&bGewicht informatie:")
                        .setLore(""
                                , "&7Gewicht: &f" + user.getWeight() + " &7Kilo."
                                , "&7Gram: &f" + user.getXp() + "&8/&f1000 &7Gram."
                                , ""
                                , "&3➥ &fGewicht kan verlaagd worden door:"
                                , "&a✔ &8| &7Goed & gezond te eten."
                                , ""
                                , "&3➥ &fGewicht kan verhoogd worden door:"
                                , "&c✗ &8| &7Slecht & ongezond te eten!").build());

                break;
            }
        }

        pStats.setItem(7, new ItemBuilder(Material.APPLE, 1)
                .setDisplayName("&aGezond eten")
                .setLore(""
                        , "&7Klik om een lijst te krijgen"
                        , "&7met al het eten dat &agezond &7is"
                        , "&7geladen in de game!").build());

        pStats.setItem(8, new ItemBuilder(Material.COOKED_BEEF, 1)
                .setDisplayName("&cOngezond eten")
                .setLore(""
                        , "&7Klik om een lijst te krijgen"
                        , "&7met al het eten dat &congezond &7is"
                        , "&7geladen in de game!").build());

        pStats.setContents(pStats.getContents());
        commandExecutor.openInventory(pStats);
        return pStats;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;

        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory().getName().contains(ChatUtils.format("&3Statistieken van: &f"))) {
            e.setCancelled(true);
            if(e.getSlot() == 7){
                healthyFoodInventory.healthyFoodMenu(p.getUniqueId());
            } if(e.getSlot() == 8){
                unHealthyFoodInventory.unHealthyFoodMenu(p.getUniqueId());
            }
        }
    }
}
