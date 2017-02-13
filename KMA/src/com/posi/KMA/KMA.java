package com.posi.KMA;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.posi.CommandExecutor.Command_createMission;
import com.posi.CommandExecutor.Command_setMobspawn;
import com.posi.CommandExecutor.Command_startMission;
import com.posi.listeners.Listener_onEntityDeath;
import com.posi.listeners.Listener_onPlayerJoin;
import com.posi.listeners.Listener_onPlayerQuit;
import com.posi.listeners.Listener_onPlayerRespawn;

public class KMA extends JavaPlugin {

	public void onEnable() {
		File file = new File("plugins//KMA//configs//lvlSystem//expTable.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				writeStandardIntoXpFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Map<String, PlayerStats> players = new HashMap<String, PlayerStats>();		//HashMap für Spieler und deren Stats, Spieler Name ist der Unique Key

		getServer().getPluginManager().registerEvents(new Listener_onPlayerJoin(this, players), this);
		
		//getServer().getPluginManager().registerEvents(new OnMissionComplete(null, players),this);  Error wenn man es nicht ausklammert
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerQuit(this, players), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onEntityDeath(this), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerRespawn(this, players), this);

		getCommand("createmission").setExecutor(new Command_createMission(this));
		
		getCommand("setmobspawn").setExecutor(new Command_setMobspawn());
		
		getCommand("startmission").setExecutor(new Command_startMission(this,players));

		getLogger().info("KMA loaded sucessfully!");
	}

	public void onDisable() {
		getLogger().info("KMA sucessfully disabled!");
	}
	
	private void writeStandardIntoXpFile(File file)
	{
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		cfg.set("1-10", 200);
		cfg.set("10-20", 200);
		cfg.set("20-30", 200);
		cfg.set("30-40", 200);
		cfg.set("40-50", 200);
		cfg.set("50-60", 200);		
		
	}

}

/*
 * TODO: Neues Event erstellen OnPlayerLvlUp in unserem System.
 */