package com.gildorymrp.essentials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gildorymrp.api.plugin.essentials.Kit;

public class KitManager {
	
	private Map<String, Kit> kits = new HashMap<String, Kit>();
	private GildorymEssentials plugin;
	
	public KitManager(GildorymEssentials plugin) {
		this.plugin = plugin;
	}
	
	public Map<String, Kit> getKits() {
		return kits;
	}
	
	public Kit getKit(String name) {
		return kits.get(name);
	}
	
	public void save() {
		File kitFile = new File(plugin.getDataFolder().getPath() + File.separator + "kits.yml");
		YamlConfiguration kitConfig = new YamlConfiguration();
		for (String kitName : kits.keySet()) {
			kitConfig.set(kitName, kits.get(kitName));
		}
		try {
			kitConfig.save(kitFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public void load() {
		File kitFile = new File(plugin.getDataFolder().getPath() + File.separator + "kits.yml");
		YamlConfiguration kitConfig = new YamlConfiguration();
		try {
			kitConfig.load(kitFile);
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		} catch (InvalidConfigurationException exception) {
			exception.printStackTrace();
		}
		for (String kitName : kitConfig.getKeys(false)) {
			kits.put(kitName, (Kit) kitConfig.get(kitName));
		}
	}
	
}
