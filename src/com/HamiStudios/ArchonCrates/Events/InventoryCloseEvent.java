package com.HamiStudios.ArchonCrates.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.HamiStudios.ArchonCrates.Main;
import com.HamiStudios.ArchonCrates.API.ArchonCratesAPI;

public class InventoryCloseEvent implements Listener {
	
	Main main;
	public InventoryCloseEvent(Main plugin) {
		main = plugin;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onInvClose(org.bukkit.event.inventory.InventoryCloseEvent event) {
		
		if(event.getInventory().getType().equals(InventoryType.CHEST)) {
			
			String inventoryName = ChatColor.stripColor(event.getInventory().getTitle());
			String crateTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Crate Title")));
			
			final Player player = (Player) event.getPlayer();
			final Inventory inv = event.getInventory();
			
			ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
			
			if(inventoryName.equalsIgnoreCase(crateTitle)) {
				if(acAPI.isPlayerInCrate(player) == true) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
						@Override
						public void run() {
							player.openInventory(inv);
						}
					}, 1);
				}
			}
			
		}
		
	}

}
