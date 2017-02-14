package com.posi.CommandExecutor;

import java.util.Map;
import java.util.Vector;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.posi.Events.OnMissionComplete;
import com.posi.Events.OnMissionStart;
import com.posi.KMA.KMA;
import com.posi.KMA.PlayerStats;

public class Command_startMission implements CommandExecutor {

	private KMA plugin;
	private Map<String, PlayerStats> players;

	public Command_startMission(KMA plugin, Map<String, PlayerStats> players) {
		this.plugin = plugin;
		this.players = players;
	}
//
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		//safe command draus mochen
		if (cmd.getName().equals("startmission")) {
			if (cs instanceof Player) {
				Player pl = (Player) cs;				
				Vector<Player> p = new Vector<Player>();
				p.add(pl);
				plugin.getServer().getPluginManager().callEvent(new OnMissionComplete(p, players, args[0]))  /*OnMissionStart(pl, players, args[0]))*/;

			} else {
				return false;
			}
		}
		return true;
	}

}
