package com.posi.listeners;

import java.util.Map;
import java.util.Vector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.posi.Events.OnMissionComplete;
import com.posi.KMA.KMA;
import com.posi.KMA.PlayerStats;

public class Listener_onPlayerDeath implements Listener{
	
	private Map<String, PlayerStats> players;
	private KMA plugin;
	
	public Listener_onPlayerDeath(KMA plugin, Map<String, PlayerStats> players) {
		this.players=players;
		this.plugin = plugin;
	}


	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if (players.get(p.getName()).getpHeadhunter()==null) {
			
		}
		else
		{
			Player pHeadHunter = e.getEntity().getKiller();
			
			 if (pHeadHunter==players.get(p.getName()).getpHeadhunter()) {
				 Vector<Player> pVec = new Vector<Player>();
				 pVec.add(e.getEntity().getKiller());		
				 	players.get(p.getName()).setpHeadhunter(null);        //der Headhunter Auftrag ist somit abgeschlossen!
					plugin.getServer().getPluginManager().callEvent(new OnMissionComplete(pVec, players, players.get(pHeadHunter.getName()).getMission())); 	//"test" muss noch irgendwie ersetzt werden. keine Ahnung wie. Irgendwie 
			 }																																					//muss ich wissen welche mission der headhunter gespielt hat und sie 
			 																																					// einfügen!
			
		}
		
	}
	
}
