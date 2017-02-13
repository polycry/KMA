package com.posi.CommandExecutor;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import com.posi.Events.OnMissionComplete;
import com.posi.KMA.KMA;
import com.posi.KMA.PlayerStats;

public class Command_startMission implements CommandExecutor {

	private KMA plugin;
	private Map<String, PlayerStats> players;
	public Command_startMission(KMA plugin, Map<String, PlayerStats> players) {
		this.plugin = plugin;
		this.players = players;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.equals("startmission")) {
			if (cs instanceof Player) {
				if (args.length<1) {
					return false;
				}
				else
				{
					 plugin.getServer().getPluginManager().callEvent(new OnMissionComplete(cs,players,args[0]));
				}
				
			}
			else
			{
								
			}
		}
		return true;
	}





}
