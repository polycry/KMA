package com.posi.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.posi.KMA.KMA;
import com.posi.KMA.PlayerStats;

public class Listener_onPlayerQuit implements Listener {

	protected KMA plugin;
	protected Map<String, PlayerStats> players;

	public Listener_onPlayerQuit(KMA plugin, Map<String, PlayerStats> players) {
		this.players = players;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		File file = new File("plugins//KMA//configs//" + p.getName() + ".yml");

		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		cfg.set("money", players.get(p.getName()).getMoney());
		cfg.set("respect", players.get(p.getName()).getRespect());
		cfg.set("lvl", players.get(p.getName()).getLvl());
		cfg.set("xp", players.get(p.getName()).getXp());

		try {
			cfg.save(file);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

}
