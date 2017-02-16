package com.posi.CommandExecutor;

import java.io.File;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class Command_addVillagerMission implements CommandExecutor {

	public Command_addVillagerMission() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 2) {
			File file = new File("plugins//KMA//configs//missions//"+args[1]+".yml");
			if (!file.exists()) {
				sender.sendMessage(ChatColor.RED + "Diese Mission exestiert nicht!");
				return true;
			}
			
			File villagerShop = new File("plugins//KMA//configs//villagers.yml");
			YamlConfiguration villagerCfg = YamlConfiguration.loadConfiguration(villagerShop);
			
			if (!villagerCfg.contains(args[0])) {
				sender.sendMessage(ChatColor.RED + "Diese Mission exestiert nicht!");
				return true;
			}
			
			Vector<String> missions = new Vector<String>();
			missions.add(args[1]);
			
			for (String mission : villagerCfg.getConfigurationSection("Villager."+args[0]+".missions").getKeys(false)) {//Add old missions (unclear what happens if path is null)
				missions.addElement(mission);
			}
			
			villagerCfg.set("Villagers." + args[0] + ".missions", missions);
			sender.sendMessage(ChatColor.GREEN + "Mission wurde erfolgreich hinzugefügt!");
			
			//reload missions
			
			//coming soon
		}
		
		return false;
	}
}