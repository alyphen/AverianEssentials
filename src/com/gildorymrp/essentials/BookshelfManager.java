package com.gildorymrp.essentials;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BookshelfManager {
	
	private GildorymEssentials plugin;
	private Map<Block, Inventory> bookshelfInventories = new HashMap<Block, Inventory>();
	
	public BookshelfManager(GildorymEssentials plugin) {
		this.plugin = plugin;
	}
	
	public Map<Block, Inventory> getBookshelfInventories() {
		return bookshelfInventories;
	}
	
	public Inventory getBookshelfInventory(Block block) {
		return bookshelfInventories.get(block);
	}
	
	public void createBookshelfInventory(Block block) {
		bookshelfInventories.put(block, plugin.getServer().createInventory(null, 9, "Bookshelf"));
	}
	
	public void save() {
		File bookshelfDirectory = new File(plugin.getDataFolder().getPath() + File.separator + "bookshelves");
		if (!bookshelfDirectory.exists()) {
			bookshelfDirectory.mkdir();
		}
		for (Block block : bookshelfInventories.keySet()) {
			bookshelfDirectory = new File(plugin.getDataFolder().getPath() + File.separator + "bookshelves" + File.separator + block.getWorld().getName() + File.separator + block.getX() + File.separator + block.getY() + File.separator + block.getZ());
			if (!bookshelfDirectory.exists()) {
				bookshelfDirectory.mkdir();
			}
			YamlConfiguration bookshelfConfig = new YamlConfiguration();
			if (block.getType() == Material.BOOKSHELF) {
				bookshelfConfig.set("contents", Arrays.asList(plugin.getBookshelfInventory(block).getContents()));
			} else {
				bookshelfConfig.set("contents", null);
			}
			try {
				bookshelfConfig.save(new File(plugin.getDataFolder().getPath() + File.separator + "bookshelves" + File.separator + block.getWorld().getName() + File.separator + block.getX() + File.separator + block.getY() + File.separator + block.getZ() + File.separator + "bookshelf.yml"));
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		plugin.saveConfig();
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		File bookshelfDirectory = new File(plugin.getDataFolder().getPath() + File.separator + "bookshelves");
		if (bookshelfDirectory.exists()) {
			for (File worldDirectory : bookshelfDirectory.listFiles()) {
				for (File xDirectory : worldDirectory.listFiles()) {
					for (File yDirectory : xDirectory.listFiles()) {
						for (File zDirectory : yDirectory.listFiles()) {
							Inventory inventory = plugin.getServer().createInventory(null, 9, "Bookshelf");
							for (ItemStack itemStack : (List<ItemStack>) plugin.getConfig().get("contents")) {
								if (itemStack != null) {
									inventory.addItem(itemStack);
								}
							}
							bookshelfInventories.put(plugin.getServer().getWorld(worldDirectory.getName()).getBlockAt(Integer.parseInt(xDirectory.getName()), Integer.parseInt(yDirectory.getName()), Integer.parseInt(zDirectory.getName())), inventory);
						}
					}
				}
			}
		}
	}
	
}