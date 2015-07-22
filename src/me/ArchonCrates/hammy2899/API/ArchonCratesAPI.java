package me.ArchonCrates.hammy2899.API;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import me.ArchonCrates.hammy2899.DefaultFiles;
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
import org.bukkit.block.Block;
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
		
		DefaultFiles dFiles = new DefaultFiles(main);
		
		Player target = Bukkit.getPlayer(playerName);
		
		int amount = keyAmount;
		int keyType = dFiles.getKeys().getInt("Keys." + keyName + ".itemId");
		String keyMat = Material.getMaterial(keyType)+"";
		ItemStack key = new ItemStack(Material.valueOf(keyMat), amount);
		ItemMeta keyMeta = key.getItemMeta();
		keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', dFiles.getKeys().getString("Keys." + keyName + ".name")));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : dFiles.getKeys().getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', s));
		keyMeta.setLore(lore);
		
		if(dFiles.getKeys().getBoolean("Keys." + keyName + ".glow") == true) {
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

		DefaultFiles dFiles = new DefaultFiles(main);
		
		int amount = keyAmount;
		int keyType = dFiles.getKeys().getInt("Keys." + keyName + ".itemId");
		String keyMat = Material.getMaterial(keyType)+"";
		ItemStack key = new ItemStack(Material.valueOf(keyMat), amount);
		ItemMeta keyMeta = key.getItemMeta();
		keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', dFiles.getKeys().getString("Keys." + keyName + ".name")));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : dFiles.getKeys().getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', s));
		keyMeta.setLore(lore);
		
		if(dFiles.getKeys().getBoolean("Keys." + keyName + ".glow") == true) {
			Glow glow = new Glow(70);
			keyMeta.addEnchant(glow, 1, true);
		}
		
        key.setItemMeta(keyMeta);
        
		for(Player online : Bukkit.getOnlinePlayers()) {
			online.getInventory().addItem(key);
			online.updateInventory();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getKeyStack(String keyName) {
		
		DefaultFiles dFiles = new DefaultFiles(main);
		
		int keyType = dFiles.getKeys().getInt("Keys." + keyName + ".itemId");
		String keyMat = Material.getMaterial(keyType)+"";
		ItemStack key = new ItemStack(Material.valueOf(keyMat));
		ItemMeta keyMeta = key.getItemMeta();
		keyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', dFiles.getKeys().getString("Keys." + keyName + ".name")));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : dFiles.getKeys().getStringList("Keys." + keyName + ".lore")) lore.add(ChatColor.translateAlternateColorCodes('&', s));
		keyMeta.setLore(lore);
		
		if(dFiles.getKeys().getBoolean("Keys." + keyName + ".glow") == true) {
			Glow glow = new Glow(70);
			keyMeta.addEnchant(glow, 1, true);
		}
		
        key.setItemMeta(keyMeta);
		
        return key;
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

		DefaultFiles dFiles = new DefaultFiles(main);
		
		if(dFiles.getKeys().contains("Keys." + keyName)) {
			return true;
		}
		return false;
	}

	
	// Language method
	public String getLangMessage(LangMessages messageType) {

		DefaultFiles dFiles = new DefaultFiles(main);
		
		String message = "";
		
		if(messageType.equals(LangMessages.PREFIX)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Prefix"));
		}
		if(messageType.equals(LangMessages.ERROR)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Error Message"));
		}
		if(messageType.equals(LangMessages.NOPERM)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("No Permission"));
		}
		if(messageType.equals(LangMessages.PLAYERONLYCMD)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Player Only Command"));
		}
		if(messageType.equals(LangMessages.RELOAD)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.reload"));
		}
		if(messageType.equals(LangMessages.CREATEALREADY)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.create.alreadyCrate"));
		}
		if(messageType.equals(LangMessages.CREATED)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.create.created"));
		}
		if(messageType.equals(LangMessages.NOCRATETYPE)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.create.noCrateType"));
		}
		if(messageType.equals(LangMessages.REMOVED)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.remove.removed"));
		}
		if(messageType.equals(LangMessages.NOTCRATE)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.remove.notCrate"));
		}
		if(messageType.equals(LangMessages.NOCRATES)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.remove.noCrates"));
		}
		if(messageType.equals(LangMessages.BLOCKNOTCRATE)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.remove.blockNotCrate"));
		}
		if(messageType.equals(LangMessages.GIVEN)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.giveKey.given"));
		}
		if(messageType.equals(LangMessages.NOTONLINE)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.giveKey.notOnline"));
		}
		if(messageType.equals(LangMessages.BUYCREATED)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Signs.buy.created"));
		}
		if(messageType.equals(LangMessages.BUYREMOVED)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Signs.buy.removed"));
		}
		if(messageType.equals(LangMessages.BUY)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Signs.buy.buy"));
		}
		if(messageType.equals(LangMessages.NOMONEY)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Signs.buy.noMoney"));
		}
		if(messageType.equals(LangMessages.CRATEINUSE)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Crate In Use"));
		}
		if(messageType.equals(LangMessages.NOECOPLUGIN)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Signs.No economy plugin"));
		}
		if(messageType.equals(LangMessages.NOKEY)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.giveKey.No key"));
		}
		if(messageType.equals(LangMessages.GIVENALLKEY)) {
			message = ChatColor.translateAlternateColorCodes('&', dFiles.getLanguage().getString("Commands.giveKey.given all"));
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

	@SuppressWarnings("deprecation")
	public void createCrate(Player player) {
		
		Block block = player.getTargetBlock(null, 10);
		int blockId = block.getTypeId();
		int crateId = main.getConfig().getInt("Crate Type");
		
		if(blockId == crateId) {
			
			double x = block.getLocation().getX();
			double y = block.getLocation().getY();
			double z = block.getLocation().getZ();
			
			if(main.getCrates().contains("Crates")) {
				ArrayList<String> currentCrates = new ArrayList<String>();
				for(String s : main.getCrates().getConfigurationSection("Crates").getKeys(false)) {
					currentCrates.add(s);
				}
				if(currentCrates.size() > 0) {
					for(String s : currentCrates) {
						if((main.getCrates().getDouble("Crates." + s + ".x") == x) && (main.getCrates().getDouble("Crates." + s + ".y") == y) && (main.getCrates().getDouble("Crates." + s + ".z") == z)) {
							player.sendMessage(getLangMessage(LangMessages.PREFIX) + getLangMessage(LangMessages.CREATEALREADY));
							return;
						}
					}
				}
				
				int crateID = main.getCrates().getInt("Crate ID");
				
				main.getCrates().set("Crates." + crateID + ".x", x);
				main.getCrates().set("Crates." + crateID + ".y", y);
				main.getCrates().set("Crates." + crateID + ".z", z);
				main.getCrates().set("Crate ID", crateID+1);
				main.saveCrates();
				
				player.sendMessage(getLangMessage(LangMessages.PREFIX) + getLangMessage(LangMessages.CREATED));
			}
			else{
				int crateID = main.getCrates().getInt("Crate ID");
				
				main.getCrates().set("Crates." + crateID + ".x", x);
				main.getCrates().set("Crates." + crateID + ".y", y);
				main.getCrates().set("Crates." + crateID + ".z", z);
				main.getCrates().set("Crate ID", crateID+1);
				main.saveCrates();
				
				player.sendMessage(getLangMessage(LangMessages.PREFIX) + getLangMessage(LangMessages.CREATED));
			}
			
		}
		else{
			player.sendMessage(getLangMessage(LangMessages.PREFIX) + getLangMessage(LangMessages.NOCRATETYPE).replaceAll("<crateType>", Material.getMaterial(crateId).toString().toLowerCase()));
		}
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
		

		DefaultFiles dFiles = new DefaultFiles(main);
		
		
		// Get key details
		ArrayList<String> keyLoot = new ArrayList<>();
		for(String s : dFiles.getKeys().getStringList("Keys." + keyName + ".loot")) keyLoot.add(s);
		
		// dFiles crate GUI
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
		for(String s : dFiles.getCrateLoot().getConfigurationSection("Crate Loot").getKeys(false)) crateLoot.add(s);
		
		ArrayList<String> lootIds = new ArrayList<>();
		for(String s : dFiles.getCrateLoot().getConfigurationSection("Crate Loot").getKeys(false)) lootIds.add(dFiles.getCrateLoot().getString("Crate Loot." + s + ".id"));
		
		for(String s : lootIds) {
			if(!keyLoot.contains(s)) {
				for(String st : dFiles.getCrateLoot().getConfigurationSection("Crate Loot").getKeys(false)) {
					if(dFiles.getCrateLoot().getString("Crate Loot." + st + ".id").equals(s)) {
						crateLoot.remove(st);
					}
				}
			}
		}
		
		String sound = main.getConfig().getString("Open Sound");
		player.playSound(player.getLocation(), Sound.valueOf(sound), 1, 1);
		player.openInventory(crateGUI);
		
		// Crates the item
		int itemId = dFiles.getCrateLoot().getInt("Crate Loot." + crateLoot.get(0) + ".Item ID");
		String itemName = Material.getMaterial(itemId)+"";
		ItemStack item = new ItemStack(Material.valueOf(itemName), 1);
		crateGUI.setItem(13, item);
		
		
		// Starts displaying all the different items
		itemTime = 10;
		ItemTask = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {

			DefaultFiles dFiles = new DefaultFiles(main);
			
			@Override
			public void run() {
				
				int max = crateLoot.size();
				Random random = new Random();
				int nextItem = random.nextInt(max) + 0;
				
				String itemId = dFiles.getCrateLoot().getString("Crate Loot." + crateLoot.get(nextItem) + ".Item ID");
				String id = "0";
				String data = "0";
				if(itemId.contains(";")) {
					String[] itemIdANDdata = itemId.split(";");
					id = itemIdANDdata[0];
					data = itemIdANDdata[1];
				}
				if(!itemId.contains(";")) {
					id = itemId;
				}
				
				if(data.equalsIgnoreCase("0")) {
					
					String MaterialName = Material.getMaterial(Integer.parseInt(id))+"";
					ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
					String displayName = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + crateLoot.get(nextItem) + ".Name"));
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(displayName);
					item.setItemMeta(itemMeta);
					
					crateGUI.setItem(13, item);
				}
				if(!data.equalsIgnoreCase("0")) {
					
					String MaterialName = Material.getMaterial(Integer.parseInt(id))+"";
					ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
					item.setDurability((short)Integer.parseInt(data));
					String displayName = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + crateLoot.get(nextItem) + ".Name"));
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setDisplayName(displayName);
					item.setItemMeta(itemMeta);
				
					crateGUI.setItem(13, item);					
				}
 				
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

			DefaultFiles dFiles = new DefaultFiles(main);
			
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
					if(dFiles.getCrateLoot().getInt("Crate Loot." + s + ".Chance") > highest) {
						highest = dFiles.getCrateLoot().getInt("Crate Loot." + s + ".Chance");
					}
				}
				// Generates the random number
				double randomNumber = random.nextInt(highest+1);
				// Adds the items that it could be
				for(String s : crateLoot) {
					double chance = dFiles.getCrateLoot().getInt("Crate Loot." + s + ".Chance");
					if(randomNumber <= chance) {
						items.add(s);
					}
				}
				if(items.size() > 1) {
					
					int itemNum = random.nextInt(items.size());
					String itemName = items.get(itemNum);
					
					String itemId = dFiles.getCrateLoot().getString("Crate Loot." + itemName + ".Item ID");
					String id = "0";
					String data = "0";
					if(itemId.contains(";")) {
						String[] itemIdANDdata = itemId.split(";");
						id = itemIdANDdata[0];
						data = itemIdANDdata[1];
					}
					if(!itemId.contains(";")) {
						id = itemId;
					}
					
					if(data.equalsIgnoreCase("0")) {
						String MaterialName = Material.getMaterial(Integer.parseInt(id))+"";
						ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
						String displayName = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + itemName + ".Name"));
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(displayName);
						item.setItemMeta(itemMeta);
						
						crateGUI.setItem(13, item);
					}
					if(!data.equalsIgnoreCase("0")) {
						String MaterialName = Material.getMaterial(Integer.parseInt(id))+"";
						ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
						item.setDurability((short)Integer.parseInt(data));
						String displayName = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + itemName + ".Name"));
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(displayName);
						item.setItemMeta(itemMeta);
						
						crateGUI.setItem(13, item);
					}
					
				}
				if(items.size() == 1){
					
					String itemName = items.get(0);
					
					String itemId = dFiles.getCrateLoot().getString("Crate Loot." + itemName + ".Item ID");
					String id = "0";
					String data = "0";
					if(itemId.contains(";")) {
						String[] itemIdANDdata = itemId.split(";");
						id = itemIdANDdata[0];
						data = itemIdANDdata[1];
					}
					if(!itemId.contains(";")) {
						id = itemId;
					}
					
					if(data.equalsIgnoreCase("0")) {
						String MaterialName = Material.getMaterial(Integer.parseInt(id))+"";
						ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
						String displayName = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + itemName + ".Name"));
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(displayName);
						item.setItemMeta(itemMeta);
						
						crateGUI.setItem(13, item);
					}
					if(!data.equalsIgnoreCase("0")) {
						String MaterialName = Material.getMaterial(Integer.parseInt(id))+"";
						ItemStack item = new ItemStack(Material.valueOf(MaterialName), 1);
						item.setDurability((short)Integer.parseInt(data));
						String displayName = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + itemName + ".Name"));
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(displayName);
						item.setItemMeta(itemMeta);
						
						crateGUI.setItem(13, item);
					}
					
				}
				
				
				// When crate is finished
				
				ItemStack winItem = new ItemStack(crateGUI.getItem(13));
				ItemMeta winItemMeta = winItem.getItemMeta();
				String winItemName = ChatColor.stripColor(winItemMeta.getDisplayName());
				int winItemId = winItem.getTypeId();
				int winItemData = winItem.getDurability();

				String winItemConfigName = "";
				
				for(String str : crateLoot) {
					String name = ChatColor.translateAlternateColorCodes('&', dFiles.getCrateLoot().getString("Crate Loot." + str + ".Name"));
					if(ChatColor.stripColor(name).equals(winItemName)) {
						
						if(dFiles.getCrateLoot().getString("Crate Loot." + str + ".Item ID").contains(";")) {
							
							String[] itemId = dFiles.getCrateLoot().getString("Crate Loot." + str + ".Item ID").split(";");
							int id = Integer.parseInt(itemId[0]);
							int data = Integer.parseInt(itemId[1]);
							
							if((winItemId == id) && (winItemData == data)) {
								winItemConfigName = str;
								break;
							}
							continue;
						}
						else{
							if(dFiles.getCrateLoot().getInt("Crate Loot." + str + ".Item ID") == winItemId) {
								winItemConfigName = str; 
								break;
							}
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
				for(String s : dFiles.getCrateLoot().getStringList("Crate Loot." + winItemConfigName + ".Command")) commandsList.add(s);
				
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
				
				if(dFiles.getCrateLoot().getBoolean("Crate Loot." + winItemConfigName + ".Broadcast") == true) {
					String prizeName = dFiles.getCrateLoot().getString("Crate Loot." + winItemConfigName + ".Prize Name");
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
