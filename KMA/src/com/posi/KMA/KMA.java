package com.posi.KMA;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.posi.CommandExecutor.Command_createMission;
import com.posi.CommandExecutor.Command_setMissionHandler;
import com.posi.CommandExecutor.Command_setMobspawn;
import com.posi.CommandExecutor.Command_startMission;
import com.posi.listeners.Listener_onEntityDeath;
import com.posi.listeners.Listener_onPlayerDeath;
import com.posi.listeners.Listener_onPlayerJoin;
import com.posi.listeners.Listener_onPlayerQuit;
import com.posi.listeners.Listener_onPlayerRespawn;

public class KMA extends JavaPlugin {

	public void onEnable() {
		
		File ordner = new File("plugins//KMA//configs//lvlSystem");
		if (!ordner.exists()) {
			ordner.mkdirs();		 
		} 
		
		File file = new File("plugins//KMA//configs//lvlSystem//expTable.yml");
		File villagerShop = new File("plugins//KMA//configs//villagers.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				villagerShop.createNewFile();
				writeStandardIntoXpFile(file);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		loadMissionHandlers(villagerShop);

		Map<String, PlayerStats> players = new HashMap<String, PlayerStats>();		//HashMap für Spieler und deren Stats, Spieler Name ist der Unique Key

		getServer().getPluginManager().registerEvents(new Listener_onPlayerJoin(this, players), this);		
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerQuit(this, players), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onEntityDeath(this), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerRespawn(this, players), this);
		
		getServer().getPluginManager().registerEvents(new Listener_onPlayerDeath(this,players), this);

		getCommand("createmission").setExecutor(new Command_createMission(this));
		
		getCommand("setmobspawn").setExecutor(new Command_setMobspawn());
		
		getCommand("startmission").setExecutor(new Command_startMission(this, players));
		
		getCommand("setMissionHandler").setExecutor(new Command_setMissionHandler());
		
		getCommand("addVillagerMission").setExecutor(new com.posi.CommandExecutor.Command_addVillagerMission());

		getLogger().info("KMA loaded sucessfully!");
		
	}

	private void loadMissionHandlers(File villagerShop) /* Set mission handlers alias villagers */ {
		YamlConfiguration villagerCfg = YamlConfiguration.loadConfiguration(villagerShop);
		
		if (villagerCfg.get("Villagers") != null) {
			for (String id : villagerCfg.getConfigurationSection("Villagers").getKeys(false)) {
				
				World world = Bukkit.getWorld(villagerCfg.getString("Villagers."+id+".world"));
				double x = villagerCfg.getDouble("Villagers."+id+".x");
				double y = villagerCfg.getDouble("Villagers."+id+".y");
				double z = villagerCfg.getDouble("Villagers."+id+".z");
				float yaw = (float) villagerCfg.getDouble("Villagers."+id+".yaw");
				float pitch = (float) villagerCfg.getDouble("Villagers."+id+".pitch");
				
				Location loc = new Location(world, x, y, z, yaw, pitch);
				
				Villager v = (Villager) world.spawnEntity(loc, EntityType.VILLAGER);
				
				v.setCustomName("Mission-Handler");
				v.setCustomNameVisible(true);
				v.setInvulnerable(true);
				v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 356000, 356000));//give the villager the slowness effect so he can't move
				
				//missing missions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			}
		}
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
		
		try {
			cfg.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

/*
 * TODO: Neues Event erstellen OnPlayerLvlUp in unserem System.
 */