package com.posi.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.posi.KMA.KMA;
import com.posi.KMA.PlayerStats;

public class Listener_onPlayerJoin implements Listener {

	protected KMA plugin;
	protected Map<String, PlayerStats> players;

	public Listener_onPlayerJoin(KMA plugin, Map<String, PlayerStats> map2) {
		this.plugin = plugin;
		this.players = map2;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		loadPlayer(p);
		setScoreBoard(p);

	}

	public void loadPlayer(Player p) {
		
		File ordner = new File("plugins//KMA//configs//players//");
		if (!ordner.exists()) {
			ordner.mkdir();
		}

		File file = new File("plugins//KMA//configs//players//" + p.getName() + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {

			}

			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			cfg.set("money", 10);
			cfg.set("lvl", 1);
			cfg.set("respect", 0);
			cfg.set("xp", 1);

			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			PlayerStats ps = new PlayerStats(p); // laden der Stats in ein
			// Objekt

			ps.setMoney(cfg.getInt("money"));
			ps.setRespect(cfg.getInt("respect"));
			ps.setLvl(cfg.getInt("lvl"));
			ps.setXp(cfg.getInt("xp"));
			p.setLevel(cfg.getInt("lvl"));

			players.put(p.getName(), ps); // Speichern des Objekts mit dem Key
			// in eine Map

		} else {

			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			PlayerStats ps = new PlayerStats(p); // laden der Stats in ein
													// Objekt

			ps.setMoney(cfg.getInt("money"));
			ps.setRespect(cfg.getInt("respect"));
			ps.setLvl(cfg.getInt("lvl"));
			ps.setXp(cfg.getInt("xp"));
			p.setLevel(cfg.getInt("lvl"));

			players.put(p.getName(), ps); // Speichern des Objekts mit dem Key in eine Map
											
		}
	}

	private void setScoreBoard(Player p) {

		Scoreboard scb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objv = scb.registerNewObjective("test", "dummy");
		File file = new File("plugins//KMA//configs//players//" + p.getName() + ".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int lvl = cfg.getInt("lvl");
		int money = cfg.getInt("money");
		int respect = cfg.getInt("respect");
		int xp = cfg.getInt("xp");
		objv.setDisplaySlot(DisplaySlot.SIDEBAR);
		objv.setDisplayName(ChatColor.GOLD + "Stats");

		Score three = objv.getScore(ChatColor.GREEN + "XP: ");
		three.setScore(xp);
		Score two = objv.getScore(ChatColor.GREEN + "Level: ");
		two.setScore(lvl);
		Score one = objv.getScore(ChatColor.GREEN + "Geld: ");
		one.setScore(money);
		Score zero = objv.getScore(ChatColor.GREEN + "Respekt: ");
		zero.setScore(respect);

		p.setScoreboard(scb);
	}

}
