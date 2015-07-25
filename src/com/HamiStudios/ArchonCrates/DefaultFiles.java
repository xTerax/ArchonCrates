package com.HamiStudios.ArchonCrates;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DefaultFiles {

	Main main;
	public DefaultFiles (Main plugin) {
		main = plugin;
	}
	
	// Crate loot file
	public File lootFile;
	public FileConfiguration loot;
	public void saveCrateLoot() {
		
		try{
			loot.save(lootFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getCrateLoot() {
		lootFile = new File(main.getDataFolder(), "crate loot.yml");
		loot = YamlConfiguration.loadConfiguration(lootFile);
		return loot;
	}
	public void setUpCrateLoot() {
		lootFile = new File(main.getDataFolder(), "crate loot.yml");
		loot = YamlConfiguration.loadConfiguration(lootFile);
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
		loot.set("Crate Loot.Sword.Command", swordCommands);
		
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
		
		//GodApple loot
		loot.set("Crate Loot.GodApple.Item ID", "322;1");
		loot.set("Crate Loot.GodApple.Name", "&6God Apple");
		loot.set("Crate Loot.GodApple.Broadcast", true);
		loot.set("Crate Loot.GodApple.id", "godApple");
		loot.set("Crate Loot.GodApple.Chance", 5);
		loot.set("Crate Loot.GodApple.Prize Name", "GOD APPLE");
		ArrayList<String> godAppleCommands = new ArrayList<>();
		godAppleCommands.add("give <player> 322:1 1");
		loot.set("Crate Loot.GodApple.Command", godAppleCommands);
		
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
		
		//Developer head loot
		loot.set("Crate Loot.DevHead.Item ID", "397;3");
		loot.set("Crate Loot.DevHead.Name", "&cDevelopers Head");
		loot.set("Crate Loot.DevHead.Broadcast", true);
		loot.set("Crate Loot.DevHead.id", "devHead");
		loot.set("Crate Loot.DevHead.Chance", 30);
		loot.set("Crate Loot.DevHead.Prize Name", "developers head");
		ArrayList<String> devHeadCommands = new ArrayList<>();
		devHeadCommands.add("minecraft:give <player> skull 1 3 {SkullOwner:\"Hamiii\",Unbreakable:1}");
		loot.set("Crate Loot.DevHead.Command", devHeadCommands);
		
		saveCrateLoot();
	}
	public void reloadCrateLoot() {
		lootFile = new File(main.getDataFolder(), "crate loot.yml");
		loot = YamlConfiguration.loadConfiguration(lootFile);
	}
	
	// Buy sign file
	public File buySignFile;
	public FileConfiguration buySign;
	public void saveBuySign() {
		
		try{
			buySign.save(buySignFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getBuySign() {
		buySignFile = new File(main.getDataFolder(), "buy sign.yml");
		buySign = YamlConfiguration.loadConfiguration(buySignFile);
		return buySign;
	}
	public void setUpBuySign() {
		buySignFile = new File(main.getDataFolder(), "buy sign.yml");
		buySign = YamlConfiguration.loadConfiguration(buySignFile);
		buySign.set("Buy Sign.Line 1", "&7[&aBuy&7]");
		buySign.set("Buy Sign.Line 2", "&7<amount>");
		buySign.set("Buy Sign.Line 3", "&7<keyType> keys");
		buySign.set("Buy Sign.Line 4", "&7Price: <price>");
		saveBuySign();
	}
	public void reloadBuySign() {
		buySignFile = new File(main.getDataFolder(), "buy sign.yml");
		buySign = YamlConfiguration.loadConfiguration(buySignFile);
	}
	
	
	// Keys file
	public File keysFile;
	public FileConfiguration keys;
	public void saveKeys() {
		
		try{
			keys.save(keysFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getKeys() {
		keysFile = new File(main.getDataFolder(), "keys.yml");
		keys = YamlConfiguration.loadConfiguration(keysFile);
		return keys;
	}
	public void setUpKeys() {
		keysFile = new File(main.getDataFolder(), "keys.yml");
		keys = YamlConfiguration.loadConfiguration(keysFile);
		
		// Default key
		ArrayList<String> defaultLore = new ArrayList<>();
		defaultLore.add("&7Right click a crate");
		defaultLore.add("&7to use the key!");
		keys.set("Keys.default.name", "&aCrate Key");
		keys.set("Keys.default.lore", defaultLore);
		keys.set("Keys.default.itemId", 131);
		keys.set("Keys.default.glow", true);
		keys.set("Keys.default.winMessage", "&3<player> has won the prize <prize> in a crate!");
		ArrayList<String> defaultLoot = new ArrayList<>();
		defaultLoot.add("diamonds");
		defaultLoot.add("food");
		defaultLoot.add("sword");
		defaultLoot.add("gold");
		defaultLoot.add("tools");
		defaultLoot.add("crateKey");
		keys.set("Keys.default.loot", defaultLoot);
		
		// Golden key
		ArrayList<String> goldenLore = new ArrayList<>();
		goldenLore.add("&7Right click a crate");
		goldenLore.add("&7to use the key!");
		keys.set("Keys.golden.name", "&6Golden Key");
		keys.set("Keys.golden.lore", goldenLore);
		keys.set("Keys.golden.itemId", 396);
		keys.set("Keys.golden.glow", true);
		keys.set("Keys.golden.winMessage", "&6<player> has won the prize <prize> in a crate!");
		ArrayList<String> goldenLoot = new ArrayList<>();
		goldenLoot.add("diamonds");
		goldenLoot.add("sword");
		goldenLoot.add("gold");
		goldenLoot.add("godApple");
		goldenLoot.add("crateKey");
		goldenLoot.add("devHead");
		keys.set("Keys.golden.loot", goldenLoot);
		
		saveKeys();
	}
	public void reloadKeys() {
		keysFile = new File(main.getDataFolder(), "keys.yml");
		keys = YamlConfiguration.loadConfiguration(keysFile);
	}
	
	// Mob drop file
	public File mobDropFile;
	public FileConfiguration mobDrop;
	public void saveMobDrop() {
		
		try{
			mobDrop.save(mobDropFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getMobDrop() {
		mobDropFile = new File(main.getDataFolder(), "mob drop.yml");
		mobDrop = YamlConfiguration.loadConfiguration(mobDropFile);
		return mobDrop;
	}
	public void setUpMobDrop() {
		mobDropFile = new File(main.getDataFolder(), "mob drop.yml");
		mobDrop = YamlConfiguration.loadConfiguration(mobDropFile);
		
		ArrayList<String> keysThatDrop = new ArrayList<>();
		
		mobDrop.set("Enabled", true);
		
		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// BAT
		mobDrop.set("Mobs that drop.BAT.enabled", true);
		mobDrop.set("Mobs that drop.BAT.chance", "10%");
		mobDrop.set("Mobs that drop.BAT.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// CHICKEN 
		mobDrop.set("Mobs that drop.CHICKEN.enabled", true);
		mobDrop.set("Mobs that drop.CHICKEN.chance", "10%");
		mobDrop.set("Mobs that drop.CHICKEN.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// COW
		mobDrop.set("Mobs that drop.COW.enabled", true);
		mobDrop.set("Mobs that drop.COW.chance", "10%");
		mobDrop.set("Mobs that drop.COW.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// MOOSHROOM_COW
		mobDrop.set("Mobs that drop.MOOSHROOM_COW.enabled", true);
		mobDrop.set("Mobs that drop.MOOSHROOM_COW.chance", "10%");
		mobDrop.set("Mobs that drop.MOOSHROOM_COW.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// PIG
		mobDrop.set("Mobs that drop.PIG.enabled", true);
		mobDrop.set("Mobs that drop.PIG.chance", "10%");
		mobDrop.set("Mobs that drop.PIG.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// RABBIT
		mobDrop.set("Mobs that drop.RABBIT.enabled", true);
		mobDrop.set("Mobs that drop.RABBIT.chance", "10%");
		mobDrop.set("Mobs that drop.RABBIT.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SHEEP
		mobDrop.set("Mobs that drop.SHEEP.enabled", true);
		mobDrop.set("Mobs that drop.SHEEP.chance", "10%");
		mobDrop.set("Mobs that drop.SHEEP.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SQUID
		mobDrop.set("Mobs that drop.SQUID.enabled", true);
		mobDrop.set("Mobs that drop.SQUID.chance", "10%");
		mobDrop.set("Mobs that drop.SQUID.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// VILLAGER
		mobDrop.set("Mobs that drop.VILLAGER.enabled", true);
		mobDrop.set("Mobs that drop.VILLAGER.chance", "10%");
		mobDrop.set("Mobs that drop.VILLAGER.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// CAVE_SPIDER
		mobDrop.set("Mobs that drop.CAVE_SPIDER.enabled", true);
		mobDrop.set("Mobs that drop.CAVE_SPIDER.chance", "10%");
		mobDrop.set("Mobs that drop.CAVE_SPIDER.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// ENDERMAN
		mobDrop.set("Mobs that drop.ENDERMAN.enabled", true);
		mobDrop.set("Mobs that drop.ENDERMAN.chance", "10%");
		mobDrop.set("Mobs that drop.ENDERMAN.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SPIDER
		mobDrop.set("Mobs that drop.SPIDER.enabled", true);
		mobDrop.set("Mobs that drop.SPIDER.chance", "10%");
		mobDrop.set("Mobs that drop.SPIDER.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// PIG_ZOMBIE
		mobDrop.set("Mobs that drop.PIG_ZOMBIE.enabled", true);
		mobDrop.set("Mobs that drop.PIG_ZOMBIE.chance", "10%");
		mobDrop.set("Mobs that drop.PIG_ZOMBIE.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// BLAZE
		mobDrop.set("Mobs that drop.BLAZE.enabled", true);
		mobDrop.set("Mobs that drop.BLAZE.chance", "10%");
		mobDrop.set("Mobs that drop.BLAZE.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// CREEPER
		mobDrop.set("Mobs that drop.CREEPER.enabled", true);
		mobDrop.set("Mobs that drop.CREEPER.chance", "10%");
		mobDrop.set("Mobs that drop.CREEPER.keysThatDrop", keysThatDrop);
		
		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// ENDERMITE
		mobDrop.set("Mobs that drop.ENDERMITE.enabled", true);
		mobDrop.set("Mobs that drop.ENDERMITE.chance", "10%");
		mobDrop.set("Mobs that drop.ENDERMITE.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// GHAST
		mobDrop.set("Mobs that drop.GHAST.enabled", true);
		mobDrop.set("Mobs that drop.GHAST.chance", "10%");
		mobDrop.set("Mobs that drop.GHAST.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// GUARDIAN
		mobDrop.set("Mobs that drop.GUARDIAN.enabled", true);
		mobDrop.set("Mobs that drop.GUARDIAN.chance", "10%");
		mobDrop.set("Mobs that drop.GUARDIAN.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// MAGMA_CUBE
		mobDrop.set("Mobs that drop.MAGMA_CUBE.enabled", true);
		mobDrop.set("Mobs that drop.MAGMA_CUBE.chance", "10%");
		mobDrop.set("Mobs that drop.MAGMA_CUBE.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SILVERFISH
		mobDrop.set("Mobs that drop.SILVERFISH.enabled", true);
		mobDrop.set("Mobs that drop.SILVERFISH.chance", "10%");
		mobDrop.set("Mobs that drop.SILVERFISH.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SKELETON
		mobDrop.set("Mobs that drop.SKELETON.enabled", true);
		mobDrop.set("Mobs that drop.SKELETON.chance", "10%");
		mobDrop.set("Mobs that drop.SKELETON.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SLIME
		mobDrop.set("Mobs that drop.SLIME.enabled", true);
		mobDrop.set("Mobs that drop.SLIME.chance", "10%");
		mobDrop.set("Mobs that drop.SLIME.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// WITCH
		mobDrop.set("Mobs that drop.WITCH.enabled", true);
		mobDrop.set("Mobs that drop.WITCH.chance", "10%");
		mobDrop.set("Mobs that drop.WITCH.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// ZOMBIE
		mobDrop.set("Mobs that drop.ZOMBIE.enabled", true);
		mobDrop.set("Mobs that drop.ZOMBIE.chance", "10%");
		mobDrop.set("Mobs that drop.ZOMBIE.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// HORSE
		mobDrop.set("Mobs that drop.HORSE.enabled", true);
		mobDrop.set("Mobs that drop.HORSE.chance", "10%");
		mobDrop.set("Mobs that drop.HORSE.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// OCELOT
		mobDrop.set("Mobs that drop.OCELOT.enabled", true);
		mobDrop.set("Mobs that drop.OCELOT.chance", "10%");
		mobDrop.set("Mobs that drop.OCELOT.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// WOLF
		mobDrop.set("Mobs that drop.WOLF.enabled", true);
		mobDrop.set("Mobs that drop.WOLF.chance", "10%");
		mobDrop.set("Mobs that drop.WOLF.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// IRON_GOLEM
		mobDrop.set("Mobs that drop.IRON_GOLEM.enabled", true);
		mobDrop.set("Mobs that drop.IRON_GOLEM.chance", "10%");
		mobDrop.set("Mobs that drop.IRON_GOLEM.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// SNOWMAN
		mobDrop.set("Mobs that drop.SNOWMAN.enabled", true);
		mobDrop.set("Mobs that drop.SNOWMAN.chance", "10%");
		mobDrop.set("Mobs that drop.SNOWMAN.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// ENDER_DRAGON
		mobDrop.set("Mobs that drop.ENDER_DRAGON.enabled", true);
		mobDrop.set("Mobs that drop.ENDER_DRAGON.chance", "10%");
		mobDrop.set("Mobs that drop.ENDER_DRAGON.keysThatDrop", keysThatDrop);

		keysThatDrop = new ArrayList<>();
		keysThatDrop.add("default");
		keysThatDrop.add("golden");
		// WITHER
		mobDrop.set("Mobs that drop.WITHER.enabled", true);
		mobDrop.set("Mobs that drop.WITHER.chance", "10%");
		mobDrop.set("Mobs that drop.WITHER.keysThatDrop", keysThatDrop);
		
		saveMobDrop();
	}
	public void reloadMobDrop() {
		mobDropFile = new File(main.getDataFolder(), "mob drop.yml");
		mobDrop = YamlConfiguration.loadConfiguration(mobDropFile);
	}
	
	// Lang file
	public File langFile;
	public FileConfiguration lang;
	public void saveLang() {
		
		try{
			lang.save(langFile);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public FileConfiguration getLanguage() {
		langFile = new File(main.getDataFolder(), "language.yml");
		lang = YamlConfiguration.loadConfiguration(langFile);
		return lang;
	}
	public void setUpLanguage() {
		langFile = new File(main.getDataFolder(), "language.yml");
		lang = YamlConfiguration.loadConfiguration(langFile);
		// Main lang
		lang.set("Prefix", "&7[&aArchonCrates&7] ");
		lang.set("Error Message", "&cError, try /archoncrates");
		lang.set("No Permission", "&cYou do not have permission to do that!");
		lang.set("Player Only Command", "&cThis command is for players only!");
		lang.set("Crate In use", "&cThat crate is in use try again later!");
		lang.set("Cant Open Without a key", "&cYou cant open a crate without a key!");
		lang.set("Not enabled in world", "&cCrates are not enabled in this world!");
		// Commands lang
		lang.set("Commands.reload", "&aReload complete!");
		lang.set("Commands.create.alreadyCrate", "&cThat is already a crate!");
		lang.set("Commands.create.created", "&aCrate created!");
		lang.set("Commands.create.noCrateType", "&cThat block can not be a crate! Crates must be: <crateType>!");
		lang.set("Commands.remove.removed", "&aCrate removed!");
		lang.set("Commands.remove.notCrate", "&cThat is not a crate!");
		lang.set("Commands.remove.noCrates", "&cThere are no crates to remove!");
		lang.set("Commands.remove.blockNotCrate", "&cThat block is not a crate!");
		lang.set("Commands.giveKey.given", "&aGiven <amount> keys to <player>!");
		lang.set("Commands.giveKey.notOnline", "&c<player> is not online! You cant give keys to offline players!");
		lang.set("Commands.giveKey.No key", "&cThere is no key with that name!");
		lang.set("Commands.giveKey.given all", "&aGiven <amount> keys to all players!");
		// Signs lang
		lang.set("Signs.No economy plugin", "&cYou can't buy keys because Vault is not installed! Contact an Administrator");
		lang.set("Signs.buy.created", "&aBuy sign created!");
		lang.set("Signs.buy.removed", "&cBuy sign removed!");
		lang.set("Signs.buy.buy", "<amount> has been taken from your account.");
		lang.set("Signs.buy.noMoney", "&cYou do not have enought money to do that!");
		
		saveLang();
	}
	public void reloadLanguage() {
		langFile = new File(main.getDataFolder(), "language.yml");
		lang = YamlConfiguration.loadConfiguration(langFile);
	}
	
}
