package com.posi.CommandExecutor;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.posi.customObjects.KVillager;

public class Command_setMissionHandler implements CommandExecutor {

	public static Vector<com.posi.customObjects.KVillager> missionHandlers = new Vector<com.posi.customObjects.KVillager>();
	
	public Command_setMissionHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 1) {
			
			File villagerShop = new File("plugins//KMA//configs//villagers.yml");
			YamlConfiguration villagerCfg = YamlConfiguration.loadConfiguration(villagerShop);
			if (sender instanceof Player) {
				
				/*for (String id : villagerCfg.getConfigurationSection("Villagers").getKeys(false)) {
					if (id.equalsIgnoreCase(args[0])) {
						sender.sendMessage(ChatColor.RED + "Dieser Name ist schon vergeben!");
						return true;  //net return false?
					}
				}
				
				for (String string : args) {
					
				}
				*/
				
				Player p = (Player) sender;
				
				Location loc = p.getLocation();
				
				Villager v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
				
				v.setCustomName("Mission-Handler");
				v.setCustomNameVisible(true);
				v.setInvulnerable(true);
				v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 356000, 356000));//give the villager the slowness effect so he can't move
				
				//save villagers!
				double x = loc.getBlockX();
				double y = loc.getBlockY();
				double z = loc.getBlockZ();
				double yaw = loc.getYaw();
				double pitch = loc.getPitch();
				String world = loc.getWorld().getName();
				
				String id = args[0];
				
				villagerCfg.set("Villagers."+id+".x", x);
				villagerCfg.set("Villagers."+id+".y", y);
				villagerCfg.set("Villagers."+id+".z", z);
				villagerCfg.set("Villagers."+id+".yaw", yaw);
				villagerCfg.set("Villagers."+id+".pitch", pitch);
				villagerCfg.set("Villagers."+id+".world", world);
				
				try {
					villagerCfg.save(villagerShop);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				missionHandlers.addElement(new KVillager(id, v));
				
				sender.sendMessage(ChatColor.GREEN + "Mission-Handler erfolgreich erstellt!");
			}
			else {
				sender.sendMessage("Dieser Command ist nur für Spieler!");
			}
		}
		
		return false;
	}
}