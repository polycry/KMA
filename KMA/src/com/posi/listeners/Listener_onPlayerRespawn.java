package com.posi.listeners;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.posi.KMA.KMA;
import com.posi.KMA.PlayerStats;

public class Listener_onPlayerRespawn implements Listener {
	
	protected Map<String, PlayerStats> players;
	
	public Listener_onPlayerRespawn(KMA plugin, Map<String, PlayerStats> players) {
		this.players = players;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();	


	}

}
