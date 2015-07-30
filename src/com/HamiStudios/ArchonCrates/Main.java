package com.HamiStudios.ArchonCrates;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.HamiStudios.ArchonCrates.API.ArchonCratesAPI;
import com.HamiStudios.ArchonCrates.API.LangMessages;
import com.HamiStudios.ArchonCrates.API.UpdateChecker;
import com.HamiStudios.ArchonCrates.Events.BlockBreakEvent;
import com.HamiStudios.ArchonCrates.Events.EntityDeathEvent;
import com.HamiStudios.ArchonCrates.Events.InventoryClickEvent;
import com.HamiStudios.ArchonCrates.Events.InventoryCloseEvent;
import com.HamiStudios.ArchonCrates.Events.PlayerInteractEvent;
import com.HamiStudios.ArchonCrates.Events.PlayerJoinEvent;
import com.HamiStudios.ArchonCrates.Events.SignEvents;

public class Main extends JavaPlugin {
	
	// Crates file
	public File cratesFile;
	public FileConfiguration crates;
	public void saveCrates() {
		try{
			crates.save(cratesFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getCrates() {
		return crates;
	}
	public void setUpCrates() {
		crates.set("Crate ID", 0);
		saveCrates();
	}
	public void reloadCrates() {
		cratesFile = new File(getDataFolder(), "crates.yml");
		crates = YamlConfiguration.loadConfiguration(cratesFile);
	}
	
	// Signs file
	public File signsFile;
	public FileConfiguration signs;
	public void saveSigns() {
		try{
			signs.save(signsFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getSigns() {
		return signs;
	}
	public void setUpSigns() {
		signs.set("Signs", new ArrayList<>());
		signs.set("Signs ID", 0);
		saveSigns();
	}
	public void reloadSigns() {
		signsFile = new File(getDataFolder(), "signs.yml");
		signs = YamlConfiguration.loadConfiguration(signsFile);
	}
	
	// onEnable
	@Override
	public void onEnable() {
		
		new PlayerInteractEvent(this);
		new InventoryClickEvent(this);
		new EntityDeathEvent(this);
		new SignEvents(this);
		new ArchonCratesAPI(this);
		new InventoryCloseEvent(this);
		new PlayerJoinEvent(this);
		new BlockBreakEvent(this);
		
		DefaultFiles dFiles = new DefaultFiles(this);
//		ShopFile sFile = new ShopFile(this);
		
		int files = 0;
		
		if(!(new File(getDataFolder(), "config.yml").exists())) {
			files++;
			getConfig().set("Check for updates", true);
			getConfig().set("Crate Type", 54);
			getConfig().set("Crate Title", "&aCrate");
			getConfig().set("Open Sound", "CHEST_OPEN");
			getConfig().set("Win Sound", "LEVEL_UP");
			getConfig().set("Crate Effect Enabled", true);
			getConfig().set("Crate Effect", "MOBSPAWNER_FLAMES");
			getConfig().set("Scroll Sound", "NOTE_BASS");
			getConfig().set("Crate Time", 8);
			getConfig().set("Solid Background Colour", false);
			getConfig().set("Stop crate opening", true);
			ArrayList<String> worlds = new ArrayList<>();
			for(World w : Bukkit.getWorlds()) worlds.add(w.getName());
			getConfig().set("Worlds Enabled", worlds);
			saveConfig();
		}
		
		cratesFile = new File(getDataFolder(), "crates.yml");
		crates = YamlConfiguration.loadConfiguration(cratesFile);
		if(!(new File(getDataFolder(), "crates.yml").exists())) {
			files++;
			setUpCrates();
		}
		
		if(!(new File(getDataFolder(), "keys.yml").exists())) {
			files++;
			dFiles.setUpKeys();
		}
		
		if(!(new File(getDataFolder(), "crate loot.yml").exists())) {
			files++;
			dFiles.setUpCrateLoot();
		}
		
		if(!(new File(getDataFolder(), "buy sign.yml").exists())) {
			files++;
			dFiles.setUpBuySign();
		}
		
		signsFile = new File(getDataFolder(), "signs.yml");
		signs = YamlConfiguration.loadConfiguration(signsFile);
		if(!(new File(getDataFolder(), "signs.yml").exists())) {
			files++;
			setUpSigns();
		}
		
		if(!(new File(getDataFolder(), "mob drop.yml").exists())) {
			files++;
			dFiles.setUpMobDrop();
		}
		
		if(!(new File(getDataFolder(), "language.yml").exists())) {
			files++;
			dFiles.setUpLanguage();
		}
		
//		if(!(new File(getDataFolder(), "shopGUI.yml").exists())) {
//			files++;
//			sFile.setUpShopFile();
//		}
		
		// Sets-up API
		ArchonCratesAPI acAPI = new ArchonCratesAPI(this);
		
		// Register Glow Enchant
		acAPI.registerGlow();
		
		// Sets up the economey
		acAPI.setupEconomy();
		
		// Console text
		ConsoleCommandSender console;
		Server server = Bukkit.getServer();
		console = server.getConsoleSender();
		console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "]" + ChatColor.GREEN + " has been enabled!");
		
		if(files > 0) {
			
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l+&e&l-------------------------------------------------------------&a&l+"));
			
			console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Some files where missing!");
			console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Missing files have been created!");
		
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l+&e&l-------------------------------------------------------------&a&l+"));
			
		}
		
		if(getConfig().getBoolean("Check for updates") == true) {
			
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l+&e&l-------------------------------------------------------------&a&l+"));
			
			console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "Checking for updates...");
		
			UpdateChecker updateChecker = new UpdateChecker("http://dev.bukkit.org/bukkit-plugins/archoncrates/files.rss");
			
			if(updateChecker.check() == true) {
				console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.RED + "There is a new version of ArchonCrates!");
				console.sendMessage(ChatColor.GRAY + "               Version: " + ChatColor.AQUA + updateChecker.getLatestVersion());
				console.sendMessage(ChatColor.GRAY + "               Link: " + ChatColor.AQUA + updateChecker.getLatestLink());
			}
			else{
				console.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "] " + ChatColor.GREEN + ChatColor.GREEN + "ArchonCrates is up to date!");
			}
			
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l+&e&l-------------------------------------------------------------&a&l+"));
			
		}
		
		// Checks/Sets crate loot ids if none
		acAPI.ifNoCrateLootIds();
	
		
		// Applys the update changes
		ApplyUpdates.apply();
	
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
		// Sets up default files
		DefaultFiles dFiles = new DefaultFiles(this);
		
		if(cmd.getName().equalsIgnoreCase("archoncrates") || cmd.getName().equalsIgnoreCase("ac")) {
			
			if(args.length == 0) {
				
				if(sender.hasPermission("archoncrates.help") || sender.isOp() || sender instanceof ConsoleCommandSender) {
					sender.sendMessage(ChatColor.GRAY + "--=[" + ChatColor.GREEN + "ArchonCrates" + ChatColor.GRAY + "]=--");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows the help page");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates key [player] [amount] [keyType]" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Gives the player the amount of keys you enter");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates giveall [keyType] [amount]" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Gives all players the amount of keys you enter");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates keys" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows a list of all the keys");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates crate [keyType]" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Opens a crate");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates create" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Makes the block you are looking at a crate (If it is the crate type)");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates remove" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Deletes the crate you are looking at");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates reload" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Reloads all the config files!");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates info" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows info about the plugin and shows a list of useful website links");
					sender.sendMessage(ChatColor.GREEN + "/archoncrates checkupdate" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Checks to see if there is a new update for the plugin");
					
					return true;
				}
				else{
					sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
					return true;
				}
			}
			
			else if(args.length == 1) {
				
				if(args[0].equalsIgnoreCase("checkupdate")) {
					
					if(sender.hasPermission("archoncrates.checkupdate") || sender.isOp() || sender instanceof ConsoleCommandSender) {

						UpdateChecker updateChecker = new UpdateChecker("http://dev.bukkit.org/bukkit-plugins/archoncrates/files.rss");
						
						if(updateChecker.check() == true) {
							sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + ChatColor.RED + "There is a new version of ArchonCrates!");
							sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.AQUA + updateChecker.getLatestVersion());
							sender.sendMessage(ChatColor.GRAY + "Link: " + ChatColor.AQUA + updateChecker.getLatestLink());
							return true;
						}
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + ChatColor.GREEN + "ArchonCrates is up to date!");
						
						return true;
						
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
				
				else if(args[0].equalsIgnoreCase("reload")) {
					
					if(sender.hasPermission("archoncrates.reload") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						// Reload config
						reloadConfig();
						//Reload crates file
						reloadCrates();
						// Reload loot file
						dFiles.reloadCrateLoot();
						// Reload buy sign file
						dFiles.reloadBuySign();
						// Reload signs file
						reloadSigns();
						// Reload language file
						dFiles.reloadLanguage();
						// Reload keys file
						dFiles.reloadKeys();
						
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
								
								if(getCrates().contains("Crates")) {
									ArrayList<String> currentCrates = new ArrayList<String>();
									for(String s : getCrates().getConfigurationSection("Crates").getKeys(false)) {
										currentCrates.add(s);
									}
									if(currentCrates.size() > 0) {
										for(String s : currentCrates) {
											if((getCrates().getDouble("Crates." + s + ".x") == x) && (getCrates().getDouble("Crates." + s + ".y") == y) && (getCrates().getDouble("Crates." + s + ".z") == z)) {
												player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.CREATEALREADY));
												return true;
											}
										}
									}
									
									int crateID = getCrates().getInt("Crate ID");
									
									getCrates().set("Crate ID", crateID+1);
									getCrates().set("Crates." + crateID + ".x", x);
									getCrates().set("Crates." + crateID + ".y", y);
									getCrates().set("Crates." + crateID + ".z", z);
									saveCrates();
									
									player.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.CREATED));
								}
								else{
									int crateID = getCrates().getInt("Crate ID");
									
									getCrates().set("Crate ID", crateID+1);
									getCrates().set("Crates." + crateID + ".x", x);
									getCrates().set("Crates." + crateID + ".y", y);
									getCrates().set("Crates." + crateID + ".z", z);
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
								
								if(getCrates().contains("Crates")) {
									ArrayList<String> currentCrates = new ArrayList<String>();
									for(String s : getCrates().getConfigurationSection("Crates").getKeys(false)) {
										currentCrates.add(s);
									}
									if(currentCrates.size() > 0) {
										for(String s : currentCrates) {
											if((getCrates().getDouble("Crates." + s + ".x") == x) && (getCrates().getDouble("Crates." + s + ".y") == y) && (getCrates().getDouble("Crates." + s + ".z") == z)) {
												crateID = s;
												getCrates().set("Crates." + crateID, null);
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
						sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.GRAY + getServer().getPluginManager().getPlugin("ArchonCrates").getDescription().getVersion());
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
						for(String s : dFiles.getKeys().getConfigurationSection("Keys").getKeys(false)) keysList.add(s);
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
			
			else if(args.length == 3) {
				
				if(args[0].equalsIgnoreCase("giveall")) {
					
					if(sender.hasPermission("archoncrates.key.all") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						acAPI.giveKeyAll(Integer.parseInt(args[2]), args[1]);
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.GIVENALLKEY).replaceAll("<amount>", args[2]));
						return true;
						
					}
					else{
						sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOPERM));
						return true;
					}
					
				}
 				
			}
			
			else if(args.length == 4) {
				
				if(args[0].equalsIgnoreCase("key")) {
					
					if(sender.hasPermission("archoncrates.key") || sender.isOp() || sender instanceof ConsoleCommandSender) {
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(!(target == null)) {
							
							if(acAPI.checkKeyType(args[3]) == false) {
								sender.sendMessage(acAPI.getLangMessage(LangMessages.PREFIX) + acAPI.getLangMessage(LangMessages.NOKEY));
								return true;
							}
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
