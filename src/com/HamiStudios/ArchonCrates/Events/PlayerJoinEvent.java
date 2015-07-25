package com.HamiStudios.ArchonCrates.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.HamiStudios.ArchonCrates.Main;
import com.HamiStudios.ArchonCrates.API.ArchonCratesAPI;
import com.HamiStudios.ArchonCrates.API.LangMessages;
import com.HamiStudios.ArchonCrates.API.UpdateChecker;

public class PlayerJoinEvent implements Listener {
	
	Main main;
	public PlayerJoinEvent(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
		
		if(event.getPlayer().isOp()) {
			
			if(main.getConfig().getBoolean("Check for updates") == true) {
			
				UpdateChecker updateChecker = new UpdateChecker("http://dev.bukkit.org/bukkit-plugins/archoncrates/files.rss");
				ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
				Player player = event.getPlayer();
				
				if(updateChecker.check() == true) {
					player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + ChatColor.RED + "There is a new version of ArchonCrates!");
				}
				else{
					player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + ChatColor.GREEN + "ArchonCrates is up to date!");
				}
				
			}
			
		}
		
	}

}
