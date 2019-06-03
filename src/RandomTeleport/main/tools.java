package RandomTeleport.main;

import org.bukkit.ChatColor;

public class tools {

    public static int getrandom(int a, int b) { return a + (int)(Math.random() * b); }

    public static String color(String text) { return ChatColor.translateAlternateColorCodes('&', text); }

}
