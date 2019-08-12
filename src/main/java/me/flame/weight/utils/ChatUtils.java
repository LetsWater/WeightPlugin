package me.flame.weight.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String format(String input){

        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
