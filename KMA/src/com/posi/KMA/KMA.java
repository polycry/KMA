package com.posi.KMA;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import com.posi.CommandExecutor.Command_createMission;
import com.posi.listeners.Listener_onEntityDeath;
import com.posi.listeners.Listener_onPlayerJoin;
import com.posi.listeners.Listener_onPlayerQuit;
import com.posi.listeners.Listener_onPlayerRespawn;

public class KMA extends JavaPlugin {

	public void onEnable() {

		File ordner = new File("plugins//KMA//configs//");
		if (!ordner.exists()) {
			ordner.mkdir();
		}

		Map<String, PlayerStats> players = new HashMap<String, PlayerStats>();		//HashMap für Spieler und deren Stats, Spieler Name ist der Unique Key

		getServer().getPluginManager().registerEvents(new Listener_onPlayerJoin(this, players), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerQuit(this, players), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onEntityDeath(this), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerRespawn(this, players), this);

		getCommand("createmission").setExecutor(new Command_createMission(this));

		getLogger().info("KMA loaded sucessfully!");
	}

	public void onDisable() {
		getLogger().info("KMA sucessfully disabled!");
	}

}

/*
 * TODO: 
 */