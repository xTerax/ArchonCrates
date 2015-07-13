package me.ArchonCrates.hammy2899;

import java.util.ArrayList;

import me.ArchonCrates.hammy2899.API.ArchonCratesAPI;
import me.ArchonCrates.hammy2899.API.LangMessages;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvents implements Listener {
	
	Main main;
	public SignEvents(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		main = plugin;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent sign) {
		
		ArchonCratesAPI acAPI = new ArchonCratesAPI(main);
		
		Player player = sign.getPlayer();
		if(player.hasPermission("archoncrates.create.sign.buy")) {
			if(sign.getLine(0).equalsIgnoreCase("acBuy")) {
				// Values
				int amount = Integer.parseInt(sign.getLine(1));
				int price = Integer.parseInt(sign.getLine(2));
				String keyName = sign.getLine(3);
				Location signLoc = sign.getBlock().getLocation();
				double x = signLoc.getX();
				double y = signLoc.getY();
				double z = signLoc.getZ();
				World world = signLoc.getWorld();
				int id = main.signs.getInt("Signs ID");
				
				sign.setLine(0, ChatColor.translateAlternateColorCodes('&', main.buySign.getString("Buy Sign.Line 1").replaceAll("<amount>", amount+"").replaceAll("<price>", price+"").replaceAll("<keyType>", keyName)));
				sign.setLine(1, ChatColor.translateAlternateColorCodes('&', main.buySign.getString("Buy Sign.Line 2").replaceAll("<amount>", amount+"").replaceAll("<price>", price+"").replaceAll("<keyType>", keyName)));
				sign.setLine(2, ChatColor.translateAlternateColorCodes('&', main.buySign.getString("Buy Sign.Line 3").replaceAll("<amount>", amount+"").replaceAll("<price>", price+"").replaceAll("<keyType>", keyName)));
				sign.setLine(3, ChatColor.translateAlternateColorCodes('&', main.buySign.getString("Buy Sign.Line 4").replaceAll("<amount>", amount+"").replaceAll("<price>", price+"").replaceAll("<keyType>", keyName)));

				// Signs config
				main.signs.set("Signs." + id + ".amount", amount);
				main.signs.set("Signs." + id + ".price", price);
				main.signs.set("Signs." + id + ".keyType", keyName);
				main.signs.set("Signs." + id + ".location.x", x);
				main.signs.set("Signs." + id + ".location.y", y);
				main.signs.set("Signs." + id + ".location.z", z);
				main.signs.set("Signs." + id + ".location.world", world.getName());
				main.signs.set("Signs ID", id+1);
				main.saveSigns();
				
				player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.BUYCREATED));
			}
		}
		
	}
	
	@EventHandler
	public void onSignBreak(BlockBreakEvent event) {
		
		if(event.isCancelled() != true) {
			
			ArchonCratesAPI acAPI = new ArchonCratesAPI(main);	
			
			if(event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.SIGN_POST) || event.getBlock().getType().equals(Material.WALL_SIGN)) {
				event.setCancelled(true);
				Sign sign = (Sign) event.getBlock().getState();
				
				String signLine = ChatColor.stripColor(sign.getLine(0));
				String line = ChatColor.translateAlternateColorCodes('&', main.buySign.getString("Buy Sign.Line 1"));
				String linenocolour = ChatColor.stripColor(line);
				
				if(signLine.equalsIgnoreCase(linenocolour)) {
					if(event.getPlayer().hasPermission("archoncrates.remove.sign.buy")) {
						
						// Values
						Location signLoc = sign.getBlock().getLocation();
						double x = signLoc.getX();
						double y = signLoc.getY();
						double z = signLoc.getZ();
						World world = signLoc.getWorld();
						ArrayList<String> signIds = new ArrayList<>();
						for(String s : main.signs.getConfigurationSection("Signs").getKeys(false)) signIds.add(s);
						String id = "";
								
						for(String s : signIds) {
							if((main.signs.getInt("Signs." + s + ".location.x") == x) && 
									(main.signs.getInt("Signs." + s + ".location.y") == y) && 
									(main.signs.getInt("Signs." + s + ".location.z") == z) && 
									(main.signs.getString("Signs." + s + ".location.world").equalsIgnoreCase(world.getName()))) {
								id = s;
								break;
							}
						}
						
						main.signs.set("Signs." + id, null);
						main.saveSigns();
						
						event.getPlayer().sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.BUYREMOVED));
						event.setCancelled(false);
					}
					else{
						event.setCancelled(true);
						event.getPlayer().sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
					}
				}
				else{
					event.setCancelled(false);
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void signClick(PlayerInteractEvent event) {
		
		ArchonCratesAPI acAPI = new ArchonCratesAPI(main);	
		
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(event.getClickedBlock().getType().equals(Material.SIGN) || event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
				
				Sign sign = (Sign) event.getClickedBlock().getState();
				
				String signLine = ChatColor.stripColor(sign.getLine(0));
				String line = ChatColor.translateAlternateColorCodes('&', main.buySign.getString("Buy Sign.Line 1"));
				String linenocolour = ChatColor.stripColor(line);
				
				if(signLine.equalsIgnoreCase(linenocolour)) {
					
					Player player = event.getPlayer();
					if(player.hasPermission("archoncrates.sign.buy.use")) {
						// Values
						Location signLoc = sign.getBlock().getLocation();
						double x = signLoc.getX();
						double y = signLoc.getY();
						double z = signLoc.getZ();
						World world = signLoc.getWorld();
						ArrayList<String> signIds = new ArrayList<>();
						for(String s : main.signs.getConfigurationSection("Signs").getKeys(false)) signIds.add(s);
						String id = "";
								
						for(String s : signIds) {
							if((main.signs.getInt("Signs." + s + ".location.x") == x) && 
									(main.signs.getInt("Signs." + s + ".location.y") == y) && 
									(main.signs.getInt("Signs." + s + ".location.z") == z) && 
									(main.signs.getString("Signs." + s + ".location.world").equalsIgnoreCase(world.getName()))) {
								id = s;
								break;
							}
						}
						
						double price = main.signs.getDouble("Signs." + id + ".price");
						int amount = main.signs.getInt("Signs." + id + ".amount");
						String keyName = main.signs.getString("Signs." + id + ".keyType");
						
						if(acAPI.isEcoEnabled() == false) {
							player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOECOPLUGIN));
							return;
						}
						//Takes the money off them
						double balance = 0;
						balance = acAPI.getBalance(player);

						if(balance >= price) {
							acAPI.takeFromBalance(player, price);
							// Gives the player the amount of keys
							acAPI.giveKey(player.getName(), amount, keyName);
							player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.BUY).replaceAll("<amount>", price+""));
							return;
						}
						else{
							player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOMONEY));
							return;
						}
						
					}
					else{
						player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
					}
					
				}
				
			}
		}
		
	}
	
}
