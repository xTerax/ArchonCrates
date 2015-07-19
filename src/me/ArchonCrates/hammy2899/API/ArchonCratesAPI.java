package me.ArchonCrates.hammy2899.API;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import me.ArchonCrates.hammy2899.Main;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ArchonCratesAPI {
	
	Main main;
	public ArchonCratesAPI(Main plugin) {
		main = plugin;
	}
	
	// Vault setup
	private static Economy eco;
	public boolean setupEconomy() {
		ArrayList<String> plugins = new ArrayList<String>();
		for(Plugin p : main.getServer().getPluginManager().getPlugins()) plugins.add(p.getName());
		
		if(plugins.contains("Vault")) {
			RegisteredServiceProvider<Economy> ecoProvider = main.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			if(ecoProvider != null) {
				eco = ecoProvider.getProvider();
			}
			return (eco != null);
		}
		
		return false;
	}
	
	// Is economy enabled
	public boolean isEcoEnabled() {
		ArrayList<String> plugins = new ArrayList<String>();
		for(Plugin p : main.getServer().getPluginManager().getPlugins()) plugins.add(p.getName());
		
		if(plugins.contains("Vault")) {
			return true;
		}
		return false;
	}
	
	// Give key method
	@SuppressWarnings("deprecation")
	public void giveKey(String playerName, int keyAmount, String keyName) {
		Player target = Bukkit.getPlayer(playerName);
		
		int amount = keyAmount;
		int keyType = main.keys.getInt("Keys." + keyName + ".itemId");
		String keyMat = Material.getMaterial(keyType)+"";
		ItemStack key = new ItemStack(Material.valueOf(keyMat), amount);
		ItemMeta keyMeta = key.getItemMeta();
		keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.keys.getString("Keys." + keyName + ".name")));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : main.keys.getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', s));
		keyMeta.setLore(lore);
		
		if(main.keys.getBoolean("Keys." + keyName + ".glow") == true) {
			Glow glow = new Glow(70);
			keyMeta.addEnchant(glow, 1, true);
		}
		
        key.setItemMeta(keyMeta);
        
		target.getInventory().addItem(key);
		target.updateInventory();
	}
	
	// Give key all method
	@SuppressWarnings("deprecation")
	public void giveKeyAll(int keyAmount, String keyName) {

		int amount = keyAmount;
		int keyType = main.keys.getInt("Keys." + keyName + ".itemId");
		String keyMat = Material.getMaterial(keyType)+"";
		ItemStack key = new ItemStack(Material.valueOf(keyMat), amount);
		ItemMeta keyMeta = key.getItemMeta();
		keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.keys.getString("Keys." + keyName + ".name")));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : main.keys.getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', s));
		keyMeta.setLore(lore);
		
		if(main.keys.getBoolean("Keys." + keyName + ".glow") == true) {
			Glow glow = new Glow(70);
			keyMeta.addEnchant(glow, 1, true);
		}
		
        key.setItemMeta(keyMeta);
        
		for(Player online : Bukkit.getOnlinePlayers()) {
			online.getInventory().addItem(key);
			online.updateInventory();
		}
		
	}
	
	// Register Glow Enchant
	public void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow(70);
			Enchantment.registerEnchantment(glow);
		} 
		catch (IllegalArgumentException e){
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Check key type
	public boolean checkKeyType(String keyName) {
		if(main.keys.contains("Keys." + keyName)) {
			return true;
		}
		return false;
	}

	
	// Language method
	public String getLangMessage(LangMessages messageType) {
		
		String message = "";
		
		if(messageType.equals(LangMessages.PREFIX)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Prefix"));
		}
		if(messageType.equals(LangMessages.ERROR)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Error Message"));
		}
		if(messageType.equals(LangMessages.NOPERM)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("No Permission"));
		}
		if(messageType.equals(LangMessages.PLAYERONLYCMD)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Player Only Command"));
		}
		if(messageType.equals(LangMessages.RELOAD)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.reload"));
		}
		if(messageType.equals(LangMessages.CREATEALREADY)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.create.alreadyCrate"));
		}
		if(messageType.equals(LangMessages.CREATED)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.create.created"));
		}
		if(messageType.equals(LangMessages.NOCRATETYPE)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.create.noCrateType"));
		}
		if(messageType.equals(LangMessages.REMOVED)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.remove.removed"));
		}
		if(messageType.equals(LangMessages.NOTCRATE)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.remove.notCrate"));
		}
		if(messageType.equals(LangMessages.NOCRATES)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.remove.noCrates"));
		}
		if(messageType.equals(LangMessages.BLOCKNOTCRATE)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.remove.blockNotCrate"));
		}
		if(messageType.equals(LangMessages.GIVEN)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.giveKey.given"));
		}
		if(messageType.equals(LangMessages.NOTONLINE)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.giveKey.notOnline"));
		}
		if(messageType.equals(LangMessages.BUYCREATED)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Signs.buy.created"));
		}
		if(messageType.equals(LangMessages.BUYREMOVED)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Signs.buy.removed"));
		}
		if(messageType.equals(LangMessages.BUY)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Signs.buy.buy"));
		}
		if(messageType.equals(LangMessages.NOMONEY)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Signs.buy.noMoney"));
		}
		if(messageType.equals(LangMessages.CRATEINUSE)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Crate In Use"));
		}
		if(messageType.equals(LangMessages.NOECOPLUGIN)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Signs.No economy plugin"));
		}
		if(messageType.equals(LangMessages.NOKEY)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.giveKey.No key"));
		}
		if(messageType.equals(LangMessages.GIVENALLKEY)) {
			message = ChatColor.translateAlternateColorCodes('&', main.lang.getString("Commands.giveKey.given all"));
		}
		
		return message;
	}

	// Crate Effects
	public int EffectTask;
	public void crateEffect(final Location loc, final World world, final String effect) {
		
		EffectTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			
			@Override
			public void run() {
				
				world.playEffect(loc, Effect.valueOf(effect), 1);	
				
			}
			
		} , 0L, 10L);
		
	}

	// Get players balance
	public double getBalance(Player player) {
		return eco.getBalance(player);
	}
	// Add to players balance
	public void addToBalance(Player player, double amount) {
		eco.depositPlayer(player, amount);
	}
	// Take from a players balance
	public void takeFromBalance(Player player, double amount) {
		eco.withdrawPlayer(player, amount);
	}
	
	// Crate
	private int GlassTask;
	private int ItemTask;
	@SuppressWarnings("unused")
	private int DisplayTask;
	@SuppressWarnings("unused")
	private int WinTask;
	private int itemTime;
	@SuppressWarnings("deprecation")
	public void openCrate(final Player player, String keyName) {
		
		// Get key details
		ArrayList<String> keyLoot = new ArrayList<>();
		for(String s : main.keys.getStringList("Keys." + keyName + ".loot")) keyLoot.add(s);
		
		// Main crate GUI
		final Inventory crateGUI = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Crate Title")));
		
		// Sets the glass colours
		Random glassColour = new Random();
		int x = 0;
		int glassColourNumber;
		while(x != 27) {
			if(x != 13) {
				glassColourNumber = glassColour.nextInt(15) + 0;
				ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) glassColourNumber);
				ItemMeta glassMeta = glass.getItemMeta();
				glassMeta.setDisplayName(" ");
				glass.setItemMeta(glassMeta);
				crateGUI.setItem(x, glass);
				x++;
				continue;
			}
			x++;
		}
		
		// Gets and sets crate loot
		final ArrayList<String> crateLoot = new ArrayList<String>();
		for(String s : main.loot.getConfigurationSection("Crate Loot").getKeys(false)) crateLoot.add(s);
		
		ArrayList<String> lootIds = new ArrayList<>();
		for(String s : main.loot.getConfigurationSection("Crate Loot").getKeys(false)) lootIds.add(main.loot.getString("Crate Loot." + s + ".id"));
		
		for(String s : lootIds) {
			if(!keyLoot.contains(s)) {
				for(String st : main.loot.getConfigurationSection("Crate Loot").getKeys(false)) {
					if(main.loot.getString("Crate Loot." + st + ".id").equals(s)) {
						crateLoot.remove(st);
					}
				}
			}
		}
		
		String sound = main.getConfig().getString("Open Sound");
		player.playSound(player.getLocation(), Sound.valueOf(sound), 1, 1);
		player.openInventory(crateGUI);
		
		// Crates the item
		int itemId = main.loot.getInt("Crate Loot." + crateLoot.get(0) + ".Item ID");
		String itemName = Material.getMaterial(itemId)+"";
		ItemStack item = new ItemStack(Material.valueOf(itemName), 1);
		crateGUI.setItem(13, item);
		
		
		// Starts displaying all the different items
		itemTime = 10;
		ItemTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
			
			@Override
			public void run() {
				
				int max = crateLoot.size();
				Random random = new Random();
				int nextItem = random.nextInt(max) + 0;
				
				int ItemId = main.loot.getInt("Crate Loot." + crateLoot.get(nextItem) + ".Item ID");
				String MaterialName = Material.getMaterial(ItemId)+"";
				ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
				String displayName = ChatColor.translateAlternateColorCodes('&', main.loot.getString("Crate Loot." + crateLoot.get(nextItem) + ".Name"));
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(displayName);
				item.setItemMeta(itemMeta);
				
				crateGUI.setItem(13, item);
			
				player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
				
			}
		}, 0L, itemTime);
		
		// Changes glass colours
		if(main.getConfig().getString("Solid Background Colour").equalsIgnoreCase("true")) {
			GlassTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
				
				@Override
				public void run() {
					
					Random random = new Random();
					int glassData = random.nextInt(15) + 0;
					int x = 0;
					
					while(x != 27) {
						if(x != 13) {
							ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) glassData);
							ItemMeta glassMeta = glass.getItemMeta();
							glassMeta.setDisplayName(" ");
							glass.setItemMeta(glassMeta);
							
							crateGUI.setItem(x, glass);
							
							x++;
							continue;
						}
						x++;
					}					
					
				}
				
			}, 0L, 5L);
		}
		else{
			GlassTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
				
				@Override
				public void run() {
					
					int slot = 0;
					int data = 0;
					
					while(slot != 27) {
						if(slot != 13) {
							Random random = new Random();
							data = random.nextInt(15) + 0;
							
							ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) data);
							ItemMeta glassMeta = glass.getItemMeta();
							glassMeta.setDisplayName(" ");
							glass.setItemMeta(glassMeta);
							
							crateGUI.setItem(slot, glass);
							slot++;
							continue;
						}
						slot++;
						
					}
					
					
					
				}
				
			}, 0L, 5L);
		}
		
		// Crate open time
		DisplayTask = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			
			@Override
			public void run() {
				
				// Cancels the tasks
				main.getServer().getScheduler().cancelTask(ItemTask);
				main.getServer().getScheduler().cancelTask(GlassTask);
				main.getServer().getScheduler().cancelTask(EffectTask);
				
				// Makes the random
				Random random = new Random();
				// Makes the items array
				ArrayList<String> items = new ArrayList<>();
				// Makes a int of the highest chance
				int highest = 0;
				// Sets the highest chance
				for(String s : crateLoot) {
					if(main.loot.getInt("Crate Loot." + s + ".Chance") > highest) {
						highest = main.loot.getInt("Crate Loot." + s + ".Chance");
					}
				}
				// Generates the random number
				double randomNumber = random.nextInt(highest+1);
				// Adds the items that it could be
				for(String s : crateLoot) {
					double chance = main.loot.getInt("Crate Loot." + s + ".Chance");
					if(randomNumber <= chance) {
						items.add(s);
					}
				}
				if(items.size() > 1) {
					
					int itemNum = random.nextInt(items.size());
					String itemName = items.get(itemNum);
					
					int ItemId = main.loot.getInt("Crate Loot." + itemName + ".Item ID");
					String MaterialName = Material.getMaterial(ItemId)+"";
					ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
					String displayName = ChatColor.translateAlternateColorCodes('&', main.loot.getString("Crate Loot." + itemName + ".Name"));
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(displayName);
					item.setItemMeta(itemMeta);
					
					crateGUI.setItem(13, item);
					
				}
				if(items.size() == 1){
					
					String itemName = items.get(0);
					
					int ItemId = main.loot.getInt("Crate Loot." + itemName + ".Item ID");
					String MaterialName = Material.getMaterial(ItemId)+"";
					ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
					String displayName = ChatColor.translateAlternateColorCodes('&', main.loot.getString("Crate Loot." + itemName + ".Name"));
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(displayName);
					item.setItemMeta(itemMeta);
					
					crateGUI.setItem(13, item);
					
				}
				
				
				// When crate is finished
				
				ItemStack winItem = new ItemStack(crateGUI.getItem(13));
				ItemMeta winItemMeta = winItem.getItemMeta();
				String winItemName = ChatColor.stripColor(winItemMeta.getDisplayName());
				int winItemId = winItem.getTypeId();
				
				
				String winItemConfigName = "";
				
				for(String str : crateLoot) {
					String name = ChatColor.translateAlternateColorCodes('&', main.loot.getString("Crate Loot." + str + ".Name"));
					if(ChatColor.stripColor(name).equals(winItemName)) {
						if(main.loot.getInt("Crate Loot." + str + ".Item ID") == winItemId) {
							winItemConfigName = str; 
							break;
						}
					}
					
				}
				
				int slot = 0;
				while(slot != 27) {
					if(slot != 13) {							
						ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
						ItemMeta glassMeta = glass.getItemMeta();
						glassMeta.setDisplayName(" ");
						glass.setItemMeta(glassMeta);
						
						crateGUI.setItem(slot, glass);
						slot++;
						continue;
					}
					slot++;
					
				}
				
				// Commands
				ArrayList<String> commandsList = new ArrayList<String>();
				for(String s : main.loot.getStringList("Crate Loot." + winItemConfigName + ".Command")) {
					commandsList.add(s);
				}
				
				// Win sound and firework
				String winSound = main.getConfig().getString("Win Sound");
				player.playSound(player.getLocation(), Sound.valueOf(winSound), 1, 1);					
				Firework fw = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
				FireworkMeta fm = fw.getFireworkMeta();
				fm.addEffect(FireworkEffect.builder().flicker(false)
						.with(Type.BURST)
						.with(Type.BALL)
						.with(Type.STAR)
						.withColor(Color.YELLOW)
						.withColor(Color.ORANGE)
						.withColor(Color.PURPLE)
						.withColor(Color.GREEN)
						.build());
				fm.setPower(1);
				fw.setFireworkMeta(fm);
				
				int size = commandsList.size();
				while(size > 0) {
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commandsList.get(0).replaceAll("<player>", player.getName()));
					commandsList.remove(0);
					size--;
				}				
				
				// Broadcast
				
				if(main.loot.getString("Crate Loot." + winItemConfigName + ".Broadcast").equalsIgnoreCase("true")) {
					String prizeName = main.loot.getString("Crate Loot." + winItemConfigName + ".Prize Name");
					String message = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Win Message").replaceAll("<player>", player.getName()).replaceAll("<prize>", prizeName));
					Bukkit.broadcastMessage(getLangMessage(LangMessages.PREFIX) + message);
				}
				
				
			}
			
		}, main.getConfig().getInt("Crate Time")*20L);
		
		WinTask = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			
			@Override
			public void run() {

				player.closeInventory();
				
			}
		}, main.getConfig().getInt("Crate Time")*20L+30L);

	}
	
}
