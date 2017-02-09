package com.posi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.posi.KMA.KMA;

public class Listener_onEntityDeath implements Listener{
	
	public Listener_onEntityDeath(KMA plugin)
	{
				
	}
	
	
	@EventHandler 
    public void onEntityDeath (EntityDeathEvent event) {       
        event.setDroppedExp(0);
        
    }

}
