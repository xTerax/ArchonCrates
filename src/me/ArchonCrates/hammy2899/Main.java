package me.ArchonCrates.hammy2899;

import java.io.File;
import java.util.ArrayList;

import me.ArchonCrates.hammy2899.API.ArchonCratesAPI;
import me.ArchonCrates.hammy2899.API.LangMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	// Crates file
	File cratesFile;
	public FileConfiguration crates;
	public void saveCrates() {
		
		try{
			crates.save(cratesFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Crate loot
	File lootFile;
	public FileConfiguration loot;
	public void saveCrateLoot() {
		
		try{
			loot.save(lootFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Buy sign file
	File buySignFile;
	public FileConfiguration buySign;
	public void saveBuySign() {
		
		try{
			buySign.save(buySignFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Signs file
	File signsFile;
	public FileConfiguration signs;
	public void saveSigns() {
		
		try{
			signs.save(signsFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Keys file
	File keysFile;
	public FileConfiguration keys;
	public void saveKeys() {
		
		try{
			keys.save(keysFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Lang file
	File langFile;
	public FileConfiguration lang;
	public void saveLang() {
		
		try{
			lang.save(langFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// onEnable
	@Override
	public void onEnable() {
		
		new Events(this);
		new SignEvents(this);
		new ArchonCratesAPI(this);
		
		int files = 0;
		
		if(!(new File(getDataFolder(), "config.yml").exists())) {
			
			files++;
			
			getConfig().set("Crate Type", 54);
			getConfig().set("Open Sound", "CHEST_OPEN");
			getConfig().set("Win Sound", "LEVEL_UP");
			getConfig().set("Crate Effect Enabled", true);
			getConfig().set("Crate Effect", "MOBSPAWNER_FLAMES");
			getConfig().set("Crate Time", 8);
			getConfig().set("Win Message", "&3<player> has won the prize <prize> in a crate!");
			getConfig().set("Solid Background Colour", false);
			
			saveConfig();
			reloadConfig();
			
		}
		
		cratesFile = new File(getDataFolder(), "crates.yml");
		crates = YamlConfiguration.loadConfiguration(cratesFile);
		if(!cratesFile.exists()) {
			
			files++;
			
			crates.set("Crate ID", 0);
			
			saveCrates();
			
		}
		
		keysFile = new File(getDataFolder(), "keys.yml");
		keys = YamlConfiguration.loadConfiguration(keysFile);
		if(!keysFile.exists()) {
			
			files++;
			
			ArrayList<String> defaultLore = new ArrayList<>();
			defaultLore.add("&7Right click a crate");
			defaultLore.add("&7to use the key!");
			
			keys.set("Keys.default.name", "&aCrate Key");
			keys.set("Keys.default.lore", defaultLore);
			keys.set("Keys.default.itemId", 131);
			
			ArrayList<String> defaultLoot = new ArrayList<>();
			defaultLoot.add("diamonds");
			defaultLoot.add("food");
			defaultLoot.add("sword");
			defaultLoot.add("gold");
			defaultLoot.add("tools");
			defaultLoot.add("crateKey");
			keys.set("Keys.default.loot", defaultLoot);
			
			saveKeys();
			
		}
		
		lootFile = new File(getDataFolder(), "crate loot.yml");
		loot = YamlConfiguration.loadConfiguration(lootFile);
		if(!lootFile.exists()) {
			
			files++;
			
			//Diamond loot
			loot.set("Crate Loot.Diamonds.Item ID", 264);
			loot.set("Crate Loot.Diamonds.Name", "&3Diamonds");
			loot.set("Crate Loot.Diamonds.Broadcast", true);
			loot.set("Crate Loot.Diamonds.id", "diamonds");		
			loot.set("Crate Loot.Diamonds.Chance", 5);
			loot.set("Crate Loot.Diamonds.Prize Name", "10 diamonds");
			ArrayList<String> diamondCommands = new ArrayList<>();
			diamondCommands.add("give <player> 264 10");
			loot.set("Crate Loot.Diamonds.Command", diamondCommands);
			
			//Food loot
			loot.set("Crate Loot.Food.Item ID", 364);
			loot.set("Crate Loot.Food.Name", "&dFood");
			loot.set("Crate Loot.Food.Broadcast", false);
			loot.set("Crate Loot.Food.id", "food");
			loot.set("Crate Loot.Food.Chance", 40);
			loot.set("Crate Loot.Food.Prize Name", "food set");
			ArrayList<String> foodCommands = new ArrayList<>();
			foodCommands.add("give <player> 364 16");
			foodCommands.add("give <player> 366 8");
			foodCommands.add("give <player> 320 16");
			loot.set("Crate Loot.Food.Command", foodCommands);
			
			//Sword loot
			loot.set("Crate Loot.Sword.Item ID", 276);
			loot.set("Crate Loot.Sword.Name", "&bSword");
			loot.set("Crate Loot.Sword.Broadcast", true);
			loot.set("Crate Loot.Sword.id", "sword");
			loot.set("Crate Loot.Sword.Chance", 10);
			loot.set("Crate Loot.Sword.Prize Name", "diamond sword");
			ArrayList<String> swordCommands = new ArrayList<>();
			swordCommands.add("give <player> 276 1");
			getConfig().set("Crate Loot.Sword.Command", swordCommands);
			
			//Gold loot
			loot.set("Crate Loot.Gold.Item ID", 266);
			loot.set("Crate Loot.Gold.Name", "&6Gold");
			loot.set("Crate Loot.Gold.Broadcast", true);
			loot.set("Crate Loot.Gold.id", "gold");
			loot.set("Crate Loot.Gold.Chance", 15);
			loot.set("Crate Loot.Gold.Prize Name", "32 gold");
			ArrayList<String> goldCommands = new ArrayList<>();
			goldCommands.add("give <player> 266 32");
			loot.set("Crate Loot.Gold.Command", goldCommands);
			
			//Tool loot
			loot.set("Crate Loot.Tools.Item ID", 257);
			loot.set("Crate Loot.Tools.Name", "&7Tools");
			loot.set("Crate Loot.Tools.Broadcast", false);
			loot.set("Crate Loot.Tools.id", "tools");
			loot.set("Crate Loot.Tools.Chance", 20);
			loot.set("Crate Loot.Tools.Prize Name", "Iron tools");
			ArrayList<String> toolsCommands = new ArrayList<>();
			toolsCommands.add("give <player> 257 1");
			toolsCommands.add("give <player> 256 1");
			toolsCommands.add("give <player> 258 1");
			loot.set("Crate Loot.Tools.Command", toolsCommands);
			
			//Crate Key loot
			loot.set("Crate Loot.CrateKey.Item ID", 131);
			loot.set("Crate Loot.CrateKey.Name", "&aCrate Key");
			loot.set("Crate Loot.CrateKey.Broadcast", true);
			loot.set("Crate Loot.CrateKey.id", "crateKey");
			loot.set("Crate Loot.CrateKey.Chance", 10);
			loot.set("Crate Loot.CrateKey.Prize Name", "crate key");
			ArrayList<String> keyCommands = new ArrayList<>();
			keyCommands.add("archoncrates key <player> 1 default");
			loot.set("Crate Loot.CrateKey.Command", keyCommands);
			
			saveCrateLoot();
			
		}
		
		buySignFile = new File(getDataFolder(), "buy sign.yml");
		buySign = YamlConfiguration.loadConfiguration(buySignFile);
		if(!buySignFile.exists()) {
			
			files++;
			
			buySign.set("Buy Sign.Line 1", "&7[&aBuy&7]");
			buySign.set("Buy Sign.Line 2", "&7<amount>");
			buySign.set("Buy Sign.Line 3", "&7<keyType> keys");
			buySign.set("Buy Sign.Line 4", "&7Price: <price>");
			
			saveBuySign();
		}
		
		signsFile = new File(getDataFolder(), "signs.yml");
		signs = YamlConfiguration.loadConfiguration(signsFile);
		if(!signsFile.exists()) {
			
			files++;
			
			signs.set("Signs", new ArrayList<>());
			signs.set("Signs ID", 0);
			
			saveSigns();
		}
		
		langFile = new File(getDataFolder(), "language.yml");
		lang = YamlConfiguration.loadConfiguration(langFile);
		if(!langFile.exists()) {
			
			files++;
			
			// Main lang
			lang.set("Prefix", "&7[&aArchonCrates&7] ");
			lang.set("Error Message", "&cError, try /archoncrates");
			lang.set("No Permission", "&cYou do not have permission to do that command!");
			lang.set("Player Only Command", "&cThis command is for players only!");
			lang.set("Crate In use", "&cThat crate is in use try again later!");
			// Commands lang
			lang.set("Commands.reload", "&aReload complete!");
			lang.set("Commands.create.alreadyCrate", "&cThat is already a crate!");
			lang.set("Commands.create.created", "&aCrate created!");
			lang.set("Commands.create.noCrateType", "&cThat block can not be a crate! Crates must be: <crateType>!");
			lang.set("Commands.remove.removed", "&aCrate removed!");
			lang.set("Commands.remove.notCrate", "&cThat is not a crate!");
			lang.set("Commands.remove.noCrates", "&cThere are no crates to remove!");
			lang.set("Commands.remove.blockNotCrate", "&cThat block is not a crate!");
			lang.set("Commands.giveKey.given", "&aGiven <amount> keys to <player>");
			lang.set("Commands.giveKey.notOnline", "&c<player> is not online! You cant give keys to offline players!");
			// Signs lang
			lang.set("Signs.No economy plugin", "&cYou can't buy keys because Vault is not installed! Contact an Administrator");
			lang.set("Signs.buy.created", "&aBuy sign created!");
			lang.set("Signs.buy.removed", "&cBuy sign removed!");
			lang.set("Signs.buy.buy", "<amount> has been taken from your account.");
			lang.set("Signs.buy.noMoney", "&cYou do not have enought money to do that!");
			
			
			saveLang();
		}

		// Sets-up API
		ArchonCratesAPI acAPI = new ArchonCratesAPI(this);
		
		// Sets up the economey
		acAPI.setupEconomy();
		
		// Console text
		ConsoleCommandSender console;
		Server server = Bukkit.getServer();
		console = server.getConsoleSender();
		console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "]" + ChatColor.GREEN + " has been enabled!");
		
		if(files > 0) {
			console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Some files where missing!");
			console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Missing files have been created!");
		}
		
	}
	
	// onDisable
	@Override
	public void onDisable() {
	
		ConsoleCommandSender console;
		
		Server server = Bukkit.getServer();
		console = server.getConsoleSender();
		
		console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "]" + ChatColor.RED + " has been disabled!");

	}	
	
	// Commands
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// Sets-up API
		ArchonCratesAPI acAPI = new ArchonCratesAPI(this);
		
		if(cmd.getName().equalsIgnoreCase("archoncrates") || cmd.getName().equalsIgnoreCase("ac")) {
			
			if(args.length == 0) {
				
				if(sender.hasPermission("archoncrates.help") || sender.isOp() || sender instanceof ConsoleCommandSender) {
					sender.sendMessage(ChatColor.GRAY + "--=[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "]=--");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows the help page");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates key [player] [amount] [keyType]" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Gives the player the amount of keys you enter");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates keys" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows a list of all the keys");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates crate [keyType]" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Opens a crate");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates create" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Makes the block you are looking at a crate (If it is the crate type)");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates remove" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Deletes the crate you are looking at");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates reload" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Reloads all the config files!");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates info" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows info about the plugin and shows a list of useful website links");
					
					return true;
				}
				else{
					sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
					return true;
				}
			}
			
			else if(args.length == 1) {
				
				if(args[0].equalsIgnoreCase("reload")) {
					
					if(sender.hasPermission("archoncrates.reload") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						// Reload config
						reloadConfig();
						//Reload crates file
						cratesFile = new File(getDataFolder(), "crates.yml");
						crates = YamlConfiguration.loadConfiguration(cratesFile);
						// Reload loot file
						lootFile = new File(getDataFolder(), "crate loot.yml");
						loot = YamlConfiguration.loadConfiguration(lootFile);
						// Reload buy sign file
						buySignFile = new File(getDataFolder(), "buy sign.yml");
						buySign = YamlConfiguration.loadConfiguration(buySignFile);
						// Reload signs file
						signsFile = new File(getDataFolder(), "signs.yml");
						signs = YamlConfiguration.loadConfiguration(signsFile);
						// Reload language file
						langFile = new File(getDataFolder(), "language.yml");
						lang = YamlConfiguration.loadConfiguration(langFile);
						// Reload keys file
						keysFile = new File(getDataFolder(), "keys.yml");
						keys = YamlConfiguration.loadConfiguration(keysFile);
						
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.RELOAD));
						
						return true;
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
				else if(args[0].equalsIgnoreCase("create")) {
					if(sender.hasPermission("archoncrates.create") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						if(sender instanceof Player) {
							Player player = (Player) sender;
							
							Block block = player.getTargetBlock(null, 10);
							int blockId = block.getTypeId();
							int crateId = getConfig().getInt("Crate Type");
							
							if(blockId == crateId) {
								
								double x = block.getLocation().getX();
								double y = block.getLocation().getY();
								double z = block.getLocation().getZ();
								
								if(crates.contains("Crates")) {
									ArrayList<String> currentCrates = new ArrayList<String>();
									for(String s : crates.getConfigurationSection("Crates").getKeys(false)) {
										currentCrates.add(s);
									}
									if(currentCrates.size() > 0) {
										for(String s : currentCrates) {
											if((crates.getDouble("Crates." + s + ".x") == x) && (crates.getDouble("Crates." + s + ".y") == y) && (crates.getDouble("Crates." + s + ".z") == z)) {
												player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.CREATEALREADY));
												return true;
											}
										}
									}
									
									int crateID = crates.getInt("Crate ID");
									
									crates.set("Crates." + crateID + ".x", x);
									crates.set("Crates." + crateID + ".y", y);
									crates.set("Crates." + crateID + ".z", z);
									crates.set("Crate ID", crateID+1);
									saveCrates();
									
									player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.CREATED));
								}
								else{
									int crateID = crates.getInt("Crate ID");
									
									crates.set("Crates." + crateID + ".x", x);
									crates.set("Crates." + crateID + ".y", y);
									crates.set("Crates." + crateID + ".z", z);
									crates.set("Crate ID", crateID+1);
									saveCrates();
									
									player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.CREATED));
								}
								
							}
							else{
								player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOCRATETYPE).replaceAll("<crateType>", Material.getMaterial(crateId).toString().toLowerCase()));
							}
							
							return true;
							
						}
						else{
							sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.PLAYERONLYCMD));
							return true;
						}
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					if(sender.hasPermission("archoncrates.remove") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						if(sender instanceof Player) {
							Player player = (Player) sender;
							
							Block block = player.getTargetBlock(null, 10);
							int blockId = block.getTypeId();
							int crateId = getConfig().getInt("Crate Type");
							
							if(blockId == crateId) {
								
								double x = block.getLocation().getX();
								double y = block.getLocation().getY();
								double z = block.getLocation().getZ();

								String crateID = "";
								
								if(crates.contains("Crates")) {
									ArrayList<String> currentCrates = new ArrayList<String>();
									for(String s : crates.getConfigurationSection("Crates").getKeys(false)) {
										currentCrates.add(s);
									}
									if(currentCrates.size() > 0) {
										for(String s : currentCrates) {
											if((crates.getDouble("Crates." + s + ".x") == x) && (crates.getDouble("Crates." + s + ".y") == y) && (crates.getDouble("Crates." + s + ".z") == z)) {
												crateID = s;
												crates.set("Crates." + crateID, null);
												saveCrates();
												player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.REMOVED));
												return true;
											}
										}
									}
									
									player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOTCRATE));
									return true;
								}
								else{									
									player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOCRATES));
									return true;
								}
								
							}
							else{
								player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.BLOCKNOTCRATE));
								return true;
							}
							
						}
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
				}
				
				else if(args[0].equalsIgnoreCase("info")) {
					
					if(sender.hasPermission("archoncrates.info") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						sender.sendMessage(ChatColor.GREEN + "ArchonCrates Info: ");
						sender.sendMessage(ChatColor.YELLOW + "Author: " + ChatColor.GRAY + "hammy2899");
						sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.GRAY + "8.1 (Release)");
						sender.sendMessage(ChatColor.YELLOW + "Bukkit page: " + ChatColor.GRAY + "http://dev.bukkit.org/bukkit-plugins/ArchonCrates/");
						sender.sendMessage(ChatColor.YELLOW + "Author's website: " + ChatColor.GRAY + "http://www.HamiStudios.com/");
						sender.sendMessage(ChatColor.YELLOW + "Forums: " + ChatColor.GRAY + "http://www.hamistudios.com/forums/index.php?forums/archoncrates.13/");
						
						return true;
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
				
				else if(args[0].equalsIgnoreCase("keys")) {
					 
					if(sender.hasPermission("archoncrates.keys") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						StringBuilder sb = new StringBuilder();
						ArrayList<String> keysList = new ArrayList<>();
						for(String s : keys.getConfigurationSection("Keys").getKeys(false)) keysList.add(s);
						for(String s : keysList) sb.append(ChatColor.GRAY + s + ChatColor.GREEN + ", ");
						
						sender.sendMessage(ChatColor.GREEN + "Keys list: ");
						sender.sendMessage(sb.substring(0, sb.length()-2));
						
						return true;
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
	
			}
			
			else if(args.length == 2) {
				
				if(args[0].equalsIgnoreCase("crate")) {
					if(sender instanceof Player) {
						Player player = (Player) sender;
						if(player.hasPermission("archoncrates.crate.command")) {
							
							acAPI.openCrate(player, args[1]);
							
							return true;
						}
						else{
							player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
							return true;
						}
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.PLAYERONLYCMD));
						return true;
					}
					
				}
				
			}
			
			else if(args.length == 4) {
				
				if(args[0].equalsIgnoreCase("key")) {
					
					if(sender.hasPermission("archoncrates.key") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(!(target == null)) {
							
							acAPI.giveKey(args[1], Integer.parseInt(args[2]), args[3]);
							
							sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.GIVEN).replaceAll("<player>", args[1]).replaceAll("<amount>", args[2]));
							
							return true;
						}
						else{
							sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOTONLINE).replaceAll("<player>", args[1]));
							return true;
						}
						
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
				else{
					sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.ERROR));
					return true;
				}
				
			}
			else{
				sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.ERROR));
				return true;
			}
				
		}
		
	return false;
	
	}
	
}
