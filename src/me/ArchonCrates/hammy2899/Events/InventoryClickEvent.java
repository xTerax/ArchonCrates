package me.ArchonCrates.hammy2899.Events;

import me.ArchonCrates.hammy2899.Main;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryClickEvent implements Listener {

	Main main;
	public InventoryClickEvent(Main plugin) {
		main = plugin;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
		String configName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Crate Title")));
		String inventoryName = ChatColor.stripColor(event.getInventory().getTitle());
		if(inventoryName.equals(configName)) {
			event.setCancelled(true);
		}
	}
	
}
