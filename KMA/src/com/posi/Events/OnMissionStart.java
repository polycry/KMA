package com.posi.Events;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.posi.KMA.Lobby;
import com.posi.KMA.PlayerStats;

public class OnMissionStart extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String mission;
	private Player p;

	Map<String, PlayerStats> players;

	public OnMissionStart(Player p, Map<String, PlayerStats> players, String mission) {
		this.players = players;
		this.mission = mission;
		this.p = p;
		startMission();
	}

	

	private void startMission() {
		File missionfile = new File("plugins//KMA//configs//missions//" + mission + ".yml");
		YamlConfiguration missioncfg = YamlConfiguration.loadConfiguration(missionfile);

		if (missioncfg.getString("typ").equals("headhunter")){
			
			if (missioncfg.getInt("players") == 1) {
				
				p.sendMessage(ChatColor.GREEN + "[Lobby] " + ChatColor.GRAY + "Du kannst deinen Auftrag nun starten.");
				Player[] pArray = new Player[Bukkit.getOnlinePlayers().size()];
				pArray = Bukkit.getOnlinePlayers().toArray(pArray);
				Random r = new Random();
				Player pTarget = pArray[r.nextInt(Bukkit.getOnlinePlayers().size())];
				
				while(pTarget==p){
					pTarget = pArray[r.nextInt(Bukkit.getOnlinePlayers().size())];
				}				
				p.sendMessage(ChatColor.GRAY + "Dein Ziel ist " + ChatColor.RED + pTarget.getName());
				
			} else {		// Nachträglich Lobby System implementieren! Mit invite 
				
				Lobby l = new Lobby(p,mission);			
				Vector<Player> pVec = new Vector<Player>();									
				
			}
		}	

		
		
	}

	@Override
	public HandlerList getHandlers() {

		return handlers;
	}

}
