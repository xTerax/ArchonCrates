package me.ArchonCrates.hammy2899.Events;

import java.util.ArrayList;
import java.util.Random;

import me.ArchonCrates.hammy2899.DefaultFiles;
import me.ArchonCrates.hammy2899.Main;
import me.ArchonCrates.hammy2899.API.ArchonCratesAPI;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDeathEvent implements Listener {
	
	Main main;
	public EntityDeathEvent(Main plugin) {
		main = plugin;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
		
		DefaultFiles dFiles = new DefaultFiles(main);
		ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
		
		if((event.getEntity().isDead()) && (!(event.getEntity() instanceof Player))) {
			
			if(dFiles.getMobDrop().getBoolean("Enabled") == true) {
				
				Random random = new Random();
				EntityType entity = event.getEntityType();
				
				if(dFiles.getMobDrop().contains("Mobs that drop." + entity.getName().toUpperCase())) {
					if(dFiles.getMobDrop().getBoolean("Mobs that drop." + entity.getName().toUpperCase() + ".enabled") == true) {
						
						int randomNum = random.nextInt(100);
						int chance = Integer.parseInt(dFiles.getMobDrop().getString("Mobs that drop." + entity.getName().toUpperCase() + ".chance").replace("%", ""));
						
						if(randomNum <= chance) {
							
							ArrayList<String> keysThatCanDrop = new ArrayList<>();
							for(String s : dFiles.getMobDrop().getStringList("Mobs that drop." + entity.getName().toUpperCase() + ".keysThatDrop")) keysThatCanDrop.add(s);
							
							if(keysThatCanDrop.size() > 1) {
								int randomKeyNum = random.nextInt(keysThatCanDrop.size());
								event.getDrops().add(acAPI.getKeyStack(keysThatCanDrop.get(randomKeyNum)));
								return;
							}
							else{
								event.getDrops().add(acAPI.getKeyStack(keysThatCanDrop.get(0)));
								return;
							}
							
						}
						
					}
				}
				
			}
			
		}
		
	}
	
}
