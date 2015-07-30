package com.HamiStudios.ArchonCrates.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.HamiStudios.ArchonCrates.Main;
import com.HamiStudios.ArchonCrates.API.ArchonCratesAPI;
import com.HamiStudios.ArchonCrates.API.LangMessages;

public class BlockBreakEvent implements Listener {

	Main main;
	public BlockBreakEvent(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
		if(main.getConfig().getInt("Crate Type") == event.getBlock().getTypeId()) {
			
			double x = event.getBlock().getX();
			double y = event.getBlock().getY();
			double z = event.getBlock().getZ();

			if(main.crates.contains("Crates")) {
				String crateId = null;
				for(String s : main.crates.getConfigurationSection("Crates").getKeys(false)) {
					if(main.crates.getDouble("Crates." + s + ".x") == x && main.crates.getDouble("Crates." + s + ".y") == y && main.crates.getDouble("Crates." + s + ".z") == z) {
						crateId = s;
						break;
					}
				}
				if(crateId == null) {
					return;
				}
				if(event.getPlayer().hasPermission("archoncrates.remove")) {
					main.crates.set("Crates." + crateId, null);
					main.saveCrates();
					ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
					event.getPlayer().sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.REMOVED));
				}
			}
			
		}
	}
	
}
