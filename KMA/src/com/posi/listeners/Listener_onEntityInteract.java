package com.posi.listeners;

import java.io.File;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import com.posi.customObjects.KVillager;

public class Listener_onEntityInteract implements Listener {//not registered!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public Listener_onEntityInteract() {
		
	}

	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Villager) {
			Villager v = (Villager) e.getRightClicked();
			KVillager kv = null;
			for (KVillager k : com.posi.CommandExecutor.Command_setMissionHandler.missionHandlers) {
				if (k.getV() == v) {
					kv = k;
				}
			}
			if (kv == null) {
				return;
			}
			
			loadInventory(p, kv);
			e.setCancelled(true);
		}
	}


	private void loadInventory(Player p, KVillager kv) {
		
		File villagerShop = new File("plugins//KMA//configs//villagers.yml");
		YamlConfiguration villagerCfg = YamlConfiguration.loadConfiguration(villagerShop);
		
		int invSize = 0;
		Vector<String> missions = new Vector<String>();
		for (String mission : villagerCfg.getConfigurationSection("Villager." + kv.getId() + ".missions").getKeys(false)) {
			missions.add(mission);
			invSize++;
		}
		
		int b = invSize;
		int a = 0;
		while(a<1)//sichergehen das das inventar einer gültigen größe entspricht
		{
			if(invSize % 9 == 0)
			{
				a = 4;
			}
			else
			{
				invSize++;
			}
		}
		if((invSize - b*2) < 0)//wenn nicht bereits genügend platz im inventar heerscht um b zweimal unterzubringen
		{
			invSize = invSize * 2;
		}
		
		ItemStack[] is = new ItemStack[b];
		
		for (String mission : missions) {
			File file = new File("plugins//KMA//configs//missions//"+mission+".yml");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(villagerShop);
			
			String typ = cfg.getString("typ");
			
			Inventory inv = Bukkit.createInventory(null, invSize);
			
			int i = 0;
			if (typ.equals("headhunter")) {
				is[i] = new ItemStack(Material.getMaterial(397), 1);
				ItemMeta meta = is[i].getItemMeta();
				meta.setDisplayName(mission);
				inv.addItem(is[i]);
			}
			else if (typ.equals("stealth")) {
				is[i] = new ItemStack(Material.getMaterial(397), 1);
				ItemMeta meta = is[i].getItemMeta();
				meta.setDisplayName(mission);
				inv.addItem(is[i]);
			}
			i++;
		}
	}
}