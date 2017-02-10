package com.posi.KMA;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Command_setMobspawn implements CommandExecutor {//Noch nicht in der plugin.yml!

	public Command_setMobspawn() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 2) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Location loc = p.getLocation();
				
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				
				File ordner = new File("plugins//KMA//missions");
				File file = new File("plugins//KMA//missions//missions.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				String ms = "";
				for (String mission : cfg.getConfigurationSection("").getKeys(false)) {//Es ist unklar, ob nach einem leeren String gesucht werden kann.
					if (mission.equals(args[0])) {
						ms = mission;
					}
				}
				if (ms.equals("")) {
					p.sendMessage("Diese Mission exestiert nicht!");
					return true;
				}
				
				int idOfRecord = 0;
				if (cfg.contains(ms+"mobs")) {
					for (String useless : cfg.getConfigurationSection("mobs").getKeys(false)) {
						idOfRecord++;
					}
				}
				cfg.set(ms+".mobs."+ idOfRecord +".x", x);
				cfg.set(ms+".mobs."+ idOfRecord +".y", y);
				cfg.set(ms+".mobs."+ idOfRecord +".z", z);
				cfg.set(ms+".mobs."+ idOfRecord +".mobId", args[1]);
				
				
				/*
				 * 
				 * Coming soon! :)
				 * 
				 */
				
				
				p.sendMessage("Mob-Spawn wurde eingerichtet!");
			}
			else {
				sender.sendMessage("Dieser Command ist nur f�r Spieler!");
				return true;
			}
		}
		
		return false;
	}

}
