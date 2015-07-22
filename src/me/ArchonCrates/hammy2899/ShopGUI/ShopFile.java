package me.ArchonCrates.hammy2899.ShopGUI;

import java.io.File;
import java.io.IOException;

import me.ArchonCrates.hammy2899.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShopFile {

	Main main;
	public ShopFile(Main plugin) {
		main = plugin;
	}
	
	public File shopFile;
	private FileConfiguration shop;
	
	public void setUpShopFile() {
		shopFile = new File(main.getDataFolder(), "shopGUI.yml");
		shop = YamlConfiguration.loadConfiguration(shopFile);
		
		shop.set("Enabled", true);
		shop.set("Open Sound", "CHEST_OPEN");
		shop.set("Style.Background ItemId", "160;7");
		shop.set("Style.nextKeyItemId", "159;5");
		shop.set("Style.previousKeyItemId", "159;5");
		shop.set("keys.default.enabled", true);
		shop.set("keys.default.price", 60000);
		
		saveShop();
	}
	
	public FileConfiguration getShopFile() {
		shopFile = new File(main.getDataFolder(), "shopGUI.yml");
		shop = YamlConfiguration.loadConfiguration(shopFile);
		return shop;
	}
	public void saveShop() {
		try {
			shop.save(shopFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
