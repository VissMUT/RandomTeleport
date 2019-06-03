package RandomTeleport.main;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin implements Listener {

    public void onEnable() {
        getLogger().info("Запуск...");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("rtp").setExecutor(this);
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getLogger().info("Создание config.yml...");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            Permission reload = new Permission("rtp.reload");
            if (player.hasPermission(reload)) {
                reloadConfig();
                player.sendMessage("Конфигурация перезагружена");
            } else {
                player.sendMessage("Недостаточно прав!");
            }
        } else {
            int rangeDistance_x = tools.getrandom(getConfig().getInt("Config.Range.min"), getConfig().getInt("Config.Range.max"));
            int rangeDistance_y = tools.getrandom(getConfig().getInt("Config.Range.min") + 1, getConfig().getInt("Config.Range.max") - 1);
            Location playerloc = player.getLocation();
            int x = getConfig().getInt("Config.InitialCoordinate.x") + rangeDistance_x;
            int y = 150;
            int z = getConfig().getInt("Config.InitialCoordinate.z") + rangeDistance_y;
            Location newloc = new Location(Bukkit.getWorld("world"), x, y, z);
            boolean earth = false;
            while (!earth) {
                newloc.setY(y);
                if (newloc.getBlock().getType() != Material.AIR) {
                    earth = true;
                    continue;
                }
                y--;
            }
            y += 3;
            newloc.setX(x);
            newloc.setY(y);
            newloc.setZ(z);
            player.teleport(newloc);
            int distTP = (int)newloc.distance(playerloc);
            player.sendTitle(tools.color(getConfig().getString("Config.Messages.prefix")), tools.color(getConfig().getString("Config.Messages.teleport_text_start")) + String.valueOf(tools.color(" &c")) + distTP + " " + tools.color(getConfig().getString("Config.Messages.teleport_text_end")));
        }
        return false;
    }

}
