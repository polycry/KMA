package com.posi.KMA;



import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Lobby {
	
	private Player lobbyOwner;
	private String mission;
	Vector<Player> pVec = new Vector<Player>();
	
	

	public Lobby(Player p,String mission)
	{
				this.lobbyOwner=p;
				this.mission=mission;
				
				createLobby(lobbyOwner,mission);
	}
	
	private void createLobby(Player lobbyOwner, String mission)
	{
		lobbyOwner.sendMessage(ChatColor.GREEN + "[Lobby] " + ChatColor.GRAY + "/invite [name]");	
		lobbyOwner.sendMessage(ChatColor.GREEN + "[Lobby] " + ChatColor.GRAY + "/kick [name]");
		
	}
	
	
	
	
	//GETTER-SETTER

	public Player getLobbyOwner() {
		return lobbyOwner;
	}

	public void setLobbyOwner(Player lobbyOwner) {
		this.lobbyOwner = lobbyOwner;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}
	
	public Vector<Player> getpVec() {
		return pVec;
	}

	public void setpVec(Vector<Player> pVec) {
		this.pVec = pVec;
	}

	
	
}
