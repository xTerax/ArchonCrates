package com.HamiStudios.ArchonCrates.Events;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.HamiStudios.ArchonCrates.DefaultFiles;
import com.HamiStudios.ArchonCrates.Main;
import com.HamiStudios.ArchonCrates.API.ArchonCratesAPI;
import com.HamiStudios.ArchonCrates.API.Glow;
import com.HamiStudios.ArchonCrates.API.LangMessages;

public class PlayerInteractEvent implements Listener {
	
	Main main;
	public PlayerInteractEvent(Main plugin) {
		main = plugin;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(org.bukkit.event.player.PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
		DefaultFiles dFiles = new DefaultFiles(main);
		
		if(player.getItemInHand() == null || event.getClickedBlock() == null) {
			return;
		}
		
		
		Block block = event.getClickedBlock();
		
		double x = block.getLocation().getX();
		double y = block.getLocation().getY();
		double z = block.getLocation().getZ();
		
			if(main.getCrates().contains("Crates")) {
				ArrayList<String> currentCrates = new ArrayList<String>();
				for(String s : main.getCrates().getConfigurationSection("Crates").getKeys(false)) {
					currentCrates.add(s);
				}
				if(currentCrates.size() > 0) {
					for(String st : currentCrates) {
						if((main.getCrates().getDouble("Crates." + st + ".x") == x) && (main.getCrates().getDouble("Crates." + st + ".y") == y) && (main.getCrates().getDouble("Crates." + st + ".z") == z)) {
						
							if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
								return;
							}
							
							if(event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().hasLore()) {
								
								ArrayList<String> worlds = new ArrayList<>();
								for(String s : main.getConfig().getStringList("Worlds Enabled")) worlds.add(s);
								
								if(worlds.contains(event.getClickedBlock().getWorld().getName())) {
								
								// Get keys
								ArrayList<String> keyNames = new ArrayList<>();
								for(String s : dFiles.getKeys().getConfigurationSection("Keys").getKeys(false)) keyNames.add(s);
								
								String keyName = null;
								int keyItemId = 0;
								String keyDisplayName = null;
								ArrayList<String> keyLore = new ArrayList<>();
								for(String s : keyNames) {
									if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', dFiles.getKeys().getString("Keys." + s + ".name")))) {
										ArrayList<String> keyLoree = new ArrayList<>();
										for(String str : dFiles.getKeys().getStringList("Keys." + s + ".lore")) keyLoree.add(ChatColor.translateAlternateColorCodes('&', str));
										if(player.getItemInHand().getItemMeta().getLore().equals(keyLoree)) {
											if(player.getItemInHand().getTypeId() == dFiles.getKeys().getInt("Keys." + s + ".itemId")) {
												keyName = s;
												keyItemId = dFiles.getKeys().getInt("Keys." + keyName + ".itemId");
												keyDisplayName = ChatColor.translateAlternateColorCodes('&', dFiles.getKeys().getString("Keys." + keyName + ".name"));
												for(String str : dFiles.getKeys().getStringList("Keys." + keyName + ".lore")) keyLore.add(ChatColor.translateAlternateColorCodes('&', str));
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
											
											if(player.hasPermission("archoncrates.crate.use")) {
												
												event.setCancelled(true);
												int amount = player.getItemInHand().getAmount();
												if(amount == 1) {
													
													String matName2 = Material.getMaterial(keyItemId)+"";
													ItemStack key = new ItemStack(Material.valueOf(matName2), 1);
													ItemMeta keyMeta = key.getItemMeta();
													keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', dFiles.getKeys().getString("Keys." + keyName + ".name")));
													ArrayList<String> lore = new ArrayList<>();
													for(String stri : dFiles.getKeys().getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', stri));
													keyMeta.setLore(lore);
													
													if(dFiles.getKeys().getBoolean("Keys." + keyName + ".glow") == true) {
														Glow glow = new Glow(70);
														keyMeta.addEnchant(glow, 1, true);
													}
													
													key.setItemMeta(keyMeta);
													
													player.getInventory().removeItem(key);
						
													// Crate Effect
													if(main.getConfig().getBoolean("Crate Effect Enabled") == true) {
														Location effectLoc = block.getLocation();
														World world = player.getWorld();
														acAPI.crateEffect(player, effectLoc, 1, 1, world, main.getConfig().getString("Crate Effect"));
													}
													
													acAPI.openCrate(player, keyName);
												}
												else{
													player.getItemInHand().setAmount(amount-1);
													
													// Crate Effect
													if(main.getConfig().getBoolean("Crate Effect Enabled") == true) {
														Location effectLoc = block.getLocation();
														World world = player.getWorld();
														acAPI.crateEffect(player, effectLoc, 1, 1, world, main.getConfig().getString("Crate Effect"));
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
							else{
								player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOTENABLEDINWORLD));
								event.setCancelled(true);
								return;
							}
						
						}
						else{
							if(main.getConfig().getBoolean("Stop crate opening") == true) {
								event.setCancelled(true);
								player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.CANTOPENWITHOUTKEY));
							}
						}
							
					}
				}
			}
			
		}
		
	}
	
}
