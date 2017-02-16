package com.posi.CommandExecutor;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command_addVillagerMission implements CommandExecutor {

	public Command_addVillagerMission() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 1) {
			File file = new File("plugins//KMA//configs//missions//"+args[0]+".yml");
			if (!file.exists()) {
				sender.sendMessage(ChatColor.RED + "Diese Mission exestiert nicht!");
				return true;
			}
			
		}
		
		return false;
	}
}