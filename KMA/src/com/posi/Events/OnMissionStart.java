package com.posi.Events;

import java.io.File;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
		createLobby();
	}

	private void createLobby() {
		File missionfile = new File("plugins//KMA//configs//missions//" + mission);
		YamlConfiguration missioncfg = YamlConfiguration.loadConfiguration(missionfile);

		if (missioncfg.getInt("players") == 1) {
			p.sendMessage(ChatColor.GREEN + "[Lobby] " + ChatColor.GRAY + "Du kannst deinen Auftrag nun starten.");
		} else {		// Nachträglich Lobby System implementieren! Mit invite 
			p.sendMessage(ChatColor.GREEN + "[Lobby] " + ChatColor.GRAY + "Diese Mission kann man nur zu "
					+ missioncfg.getInt("players")
					+ ". spielen! Du kannst Kameraden mit '/invite [name]' in deine Mission einladen!");
			
			
		}
	}

	@Override
	public HandlerList getHandlers() {

		return handlers;
	}

}
