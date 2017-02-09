package com.posi.Events;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.posi.KMA.PlayerStats;

public class OnMissionComplete extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Vector<Player> p;
	private HashMap<String, PlayerStats> ps;
	
	
	public OnMissionComplete(Vector<Player> p, HashMap<String, PlayerStats> ps) {
		super();
		this.p = p;
		this.ps = ps;
		syncLvl();
	}

	private void syncLvl() {
		for (Player player : p) {
			PlayerStats stats = ps.get(player.getName());
			
			//Calculate Max exp
			/*Total Experience = [Level]2 + 6[Level] (at levels 0-15)
				2.5[Level]2 - 40.5[Level] + 360 (at levels 16-30)
				4.5[Level]2 - 162.5[Level] + 2220 (at level 31+)4,5*40*2-162,5*40+2220
			double totalEXP = 0;
			if (lvl >= 0 && lvl <= 15  Level zwischen 0 und 15!!!) {
				totalEXP = lvl*2+6*lvl;
			}
			else if (lvl >= 16 && lvl <= 30  Level zwischen 16 und 30!!! ) {
				totalEXP = (2.5*lvl*2-40.5*lvl+360);
			}
			else if (lvl <= 31  Level 31+!!! ) {
				totalEXP = (4.5*lvl*2-162.5*lvl+2220);//für lvl 40 braucht man 10120
			}*/
			
			//player.setExp();
			calculateNewStats(stats);
			float xpBar = calculateProcent(stats.getXp());
			player.setExp(xpBar);
			
			//update scorebord
			int lvl = stats.getLvl();
			int money = stats.getMoney();
			int respect = stats.getRespect();
			
			Scoreboard scb = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objv = scb.registerNewObjective("test", "dummy");
			objv.setDisplaySlot(DisplaySlot.SIDEBAR);
			objv.setDisplayName(ChatColor.GOLD + "Stats");

			Score three = objv.getScore(ChatColor.GREEN + "XP: ");
			three.setScore((int) stats.getXp());
			Score two = objv.getScore(ChatColor.GREEN + "Level: ");
			two.setScore(lvl);
			Score one = objv.getScore(ChatColor.GREEN + "Geld: ");
			one.setScore(money);
			Score zero = objv.getScore(ChatColor.GREEN + "Respekt: ");
			zero.setScore(respect);

			player.setScoreboard(scb);
		}
	}

	private void calculateNewStats(PlayerStats stats) {
		stats.setMoney(stats.getMoney()/*+Mission money*/);
		stats.setRespect(stats.getRespect()/*+Mission money*/);
		stats.setXp(stats.getXp()/*+Mission money*/);
	}

	private float calculateProcent(float exp) {
		return exp/1000;//Dies ist nur ein Beispiel für eine umrechnung,falls in Tausenderschritten ein lvl vergeben wird
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public Vector<Player> getPlayers() {
		return p;
	}

	public void setPlayers(Vector<Player> p) {
		this.p = p;
	}
}