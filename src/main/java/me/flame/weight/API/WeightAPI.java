package me.flame.weight.API;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.flame.weight.users.managers.UserManager;
import org.bukkit.entity.Player;

public class WeightAPI extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "weight";
    }

    @Override
    public String getAuthor() {
        return "LetsFlame_";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    public String onPlaceholderRequest(Player p, String identifier){
        if(p == null){
            return null;
        }

        if(identifier.equals("getweight")){
            return String.valueOf(UserManager.getInstance().getWeight(p.getUniqueId()));
        }

        if(identifier.equals("getxp")){
            return String.valueOf(UserManager.getInstance().getXP(p.getUniqueId()));
        }

        return null;
    }
}
