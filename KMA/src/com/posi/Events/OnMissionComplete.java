package com.posi.Events;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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
	
	private String mission;
	private HashMap<String, PlayerStats> players;
	
	
	public OnMissionComplete(Vector<Player> p, Map<String, PlayerStats> players, String mission) {
		super();
		this.p = p;	
		this.players = (HashMap<String, PlayerStats>) players;
		this.mission = mission;		
		syncLvl();
	}

	private void syncLvl() {	
			
		File fileXP = new File("plugins//KMA//configs//lvlSystem//expTable.yml");
		YamlConfiguration cfgxp = YamlConfiguration.loadConfiguration(fileXP);
		
		File fileMission = new File("plugins//KMA//configs//missions//" + mission + ".yml");
		YamlConfiguration cfgmission = YamlConfiguration.loadConfiguration(fileMission);
				
		int neededXp=0; //Kann sehr leicht Bugs verursachen wenn die Abfragen fürs Level nicht 100% stimmen							
		
		for (Player player : p) {		
			
			player.sendMessage("Juhuu!");
			
			PlayerStats stats = players.get(player.getName());			
			
			stats = calculateNewStats(stats,cfgmission);
			
			//hier werden Lvl verglichen und dementsprechend aus der cfg auslesen wie viele xp benötigt werden fürs nächste LvlUp
			if (stats.getLvl()<10) {
				neededXp = cfgxp.getInt("1-10");
			}	
	        
			//wenn man mehr Xp hat als fürs lvl benötigt wurden wird das lvl mit 1 addiert und die restlcihen Xp auf das nächste LVL übertragen
			if (stats.getXp() >= neededXp) {
				stats.setLvl(stats.getLvl()+1);
				stats.setXp(stats.getXp()-neededXp);  //falls überschüssige Xp nach dem LevelUp überbleiben sollte es mit dieser Berechnung bei nächsten Lvl angerechnet werden				
			}		
			
			float xpBar = calculateProcent(stats.getXp(),neededXp);	
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
			players.remove(player.getName());
			players.put(player.getName(), stats);
		}
	}



	private PlayerStats calculateNewStats(PlayerStats stats, YamlConfiguration cfgMission) {
		stats.setMoney(stats.getMoney() + cfgMission.getInt("money-reward"));  /*+Mission money*/
		stats.setRespect(stats.getRespect() + cfgMission.getInt("respect-reward"));  /*+Mission money*/
		stats.setXp(stats.getXp() + cfgMission.getInt("xp-reward"));  /*+Mission money*/
		return stats;
	}

	private float calculateProcent(float exp, int neededXP) {		// Diese funktion ist im Momeent eigentlich überschüssig, da es nur eine simple rechnung ist.
		return exp/neededXP; //Dies ist nur ein Beispiel für eine umrechnung,falls in Tausenderschritten ein lvl vergeben wird
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