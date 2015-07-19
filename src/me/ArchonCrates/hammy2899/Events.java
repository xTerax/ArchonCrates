package me.ArchonCrates.hammy2899;

import java.util.ArrayList;

import me.ArchonCrates.hammy2899.API.ArchonCratesAPI;
import me.ArchonCrates.hammy2899.API.Glow;
import me.ArchonCrates.hammy2899.API.LangMessages;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Events implements Listener {
	
	Main main;
	
	public Events(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		String name = ChatColor.stripColor(main.getConfig().getString("Crate Title"));
		if(ChatColor.stripColor(event.getInventory().getTitle()).equalsIgnoreCase(name)) {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().hasItemMeta()) {
			
			// Get keys
			ArrayList<String> keyNames = new ArrayList<>();
			for(String s : main.keys.getConfigurationSection("Keys").getKeys(false)) keyNames.add(s);
			
			String keyName = null;
			int keyItemId = 0;
			String keyDisplayName = null;
			ArrayList<String> keyLore = new ArrayList<>();
			for(String s : keyNames) {
				if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', main.keys.getString("Keys." + s + ".name")))) {
					ArrayList<String> keyLoree = new ArrayList<>();
					for(String st : main.keys.getStringList("Keys." + s + ".lore")) keyLoree.add(ChatColor.translateAlternateColorCodes('&', st));
					if(player.getItemInHand().getItemMeta().getLore().equals(keyLoree)) {
						if(player.getItemInHand().getTypeId() == main.keys.getInt("Keys." + s + ".itemId")) {
							keyName = s;
							keyItemId = main.keys.getInt("Keys." + keyName + ".itemId");
							keyDisplayName = ChatColor.translateAlternateColorCodes('&', main.keys.getString("Keys." + keyName + ".name"));
							for(String str : main.keys.getStringList("Keys." + keyName + ".lore")) keyLore.add(ChatColor.translateAlternateColorCodes('&', str));
							break;
						}
					}
				}
			}
			
			int crateType = main.getConfig().getInt("Crate Type");
			String matName1 = Material.getMaterial(crateType)+"";
			if(event.getClickedBlock().getType().equals(Material.valueOf(matName1))) {
				
				if(player.getItemInHand().getTypeId() == keyItemId) {
					
					if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(keyDisplayName) && player.getItemInHand().getItemMeta().getLore().equals(keyLore)) {
						
						Block block = event.getClickedBlock();
						
						double x = block.getLocation().getX();
						double y = block.getLocation().getY();
						double z = block.getLocation().getZ();
						
						if(main.crates.contains("Crates")) {
							ArrayList<String> currentCrates = new ArrayList<String>();
							for(String s : main.crates.getConfigurationSection("Crates").getKeys(false)) {
								currentCrates.add(s);
							}
							if(currentCrates.size() > 0) {
								for(String s : currentCrates) {
									if((main.crates.getDouble("Crates." + s + ".x") == x) && (main.crates.getDouble("Crates." + s + ".y") == y) && (main.crates.getDouble("Crates." + s + ".z") == z)) {
										if(player.hasPermission("archoncrates.crate.use")) {
											
											event.setCancelled(true);
											int amount = player.getItemInHand().getAmount();
											if(amount == 1) {
												
												String matName2 = Material.getMaterial(keyItemId)+"";
												ItemStack key = new ItemStack(Material.valueOf(matName2), 1);
												ItemMeta keyMeta = key.getItemMeta();
												keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.keys.getString("Keys." + keyName + ".name")));
												ArrayList<String> lore = new ArrayList<>();
												for(String st : main.keys.getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', st));
												keyMeta.setLore(lore);
												
												if(main.keys.getBoolean("Keys." + keyName + ".glow") == true) {
													Glow glow = new Glow(70);
													keyMeta.addEnchant(glow, 1, true);
												}
												
												key.setItemMeta(keyMeta);
												
												player.getInventory().removeItem(key);

												// Crate Effect
												if(main.getConfig().getBoolean("Crate Effect Enabled") == true) {
													Location effectLoc = block.getLocation();
													World world = player.getWorld();
													acAPI.crateEffect(effectLoc, world, "MOBSPAWNER_FLAMES");
												}
												
												acAPI.openCrate(player, keyName);
											}
											else{
												player.getItemInHand().setAmount(amount-1);
												
												// Crate Effect
												if(main.getConfig().getBoolean("Crate Effect Enabled") == true) {
													Location effectLoc = block.getLocation();
													World world = player.getWorld();
													acAPI.crateEffect(effectLoc, world, "MOBSPAWNER_FLAMES");
												}
												
												acAPI.openCrate(player, keyName);
											}

										}
										else{
											player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
											event.setCancelled(true);
										}
									}
								}
							}
						}
						
					}
					
					
				}
				
			}
 			
		}
		
	}
	
}