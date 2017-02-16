package com.posi.CommandExecutor;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.posi.KMA.KMA;

public class Command_createMission implements CommandExecutor {

	public Command_createMission(KMA plugin) {

	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] arg) {

		if (cs instanceof Player) {
			Player p = (Player) cs;
			if (cmd.getName().equalsIgnoreCase("createmission")) {
				if (p.hasPermission("createmission")) {
					if (arg.length < 7) {
						return false;
					} else if (arg.length == 7) {
						if (!arg[5].equalsIgnoreCase("headhunter") && !arg[5].equalsIgnoreCase("stealth")			//Hier müssen Missionstypen geaddet werden!
								&& !arg[5].equalsIgnoreCase("main") && !arg[5].equalsIgnoreCase("protection")) {
							return false;
						} else {
							createMission(p, arg);
						}

					}
				}
			}
		} else {
			cs.sendMessage("Dieser Command ist nur für Spieler!");
		}

		return true;
	}

	public void createMission(Player p, String[] arg) {

		File ordner = new File("plugins//KMA//configs//missions");
		File file = new File("plugins//KMA//configs//missions//"+arg[0]+".yml");

		if (!ordner.exists()) {
			ordner.mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				p.sendMessage("File wurde erstellt!");
			}
		}

		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		cfg.set("name", arg[0]);
		cfg.set("cost", Integer.parseInt(arg[1]));
		cfg.set("money-reward", Integer.parseInt(arg[2]));
		cfg.set("xp-reward", Integer.parseInt(arg[3]));
		cfg.set("respect-reward", Integer.parseInt(arg[4]));
		cfg.set("typ", arg[5]);
		cfg.set("players", Integer.parseInt(arg[6]));
		

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		p.sendMessage("Mission erfolgreich erstellt!");
	}

}
