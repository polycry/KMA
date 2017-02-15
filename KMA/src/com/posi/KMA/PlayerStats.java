package com.posi.KMA;

import org.bukkit.entity.Player;

public class PlayerStats {

	private int money, respect, lvl, xp;
	private Player pHeadhunter;


	public PlayerStats(Player p) {
		
	}

	// GETTER-SETTER
	

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
