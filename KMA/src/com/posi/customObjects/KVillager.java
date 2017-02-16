package com.posi.customObjects;

import org.bukkit.entity.Villager;

public class KVillager {

	private String id;
	private Villager v;
	public KVillager(String id, Villager v) {
		super();
		this.id = id;
		this.v = v;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Villager getV() {
		return v;
	}
	public void setV(Villager v) {
		this.v = v;
	}
}
