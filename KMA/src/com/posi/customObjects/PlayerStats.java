package com.posi.customObjects;

import org.bukkit.entity.Player;

public class PlayerStats {

	private int money, respect, lvl, xp;
	private Player pHeadhunter;
	private String mission;




	public PlayerStats(Player p) {
		
	}

	// GETTER-SETTER
	
	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}
	

	public Player getpHeadhunter() {
		return pHeadhunter;
	}

	public void setpHeadhunter(Player pHeadhunter) {
		this.pHeadhunter = pHeadhunter;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;

	}

	public int getRespect() {
		return respect;
	}

	public void setRespect(int respect) {
		this.respect = respect;

	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;

	}

}
