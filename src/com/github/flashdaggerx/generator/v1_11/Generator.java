package com.github.flashdaggerx.generator.v1_11;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import com.github.flashdaggerx.generator.FlexFile;
import com.github.flashdaggerx.generator.RandomLoc;

public class Generator {
	// TODO Fix the inheritance. Methods are scattered where they don't belong.
	private Random rand;
	private FlexFile file;
	private RandomItem itemGen;
	private RandomLoc locGen;

	private int toAdd;

	private Block block;
	private Chest chest;
	private Inventory chestInv;

	public Generator() {
		rand = new Random();
		file = new FlexFile();
		file.refreshConfiguration();
		itemGen = new RandomItem(file, rand);
		locGen = new RandomLoc(file, rand);
		refreshConfiguration();
	}

	public void refreshConfiguration() {
		file.refreshConfiguration();
		itemGen.refreshPoolStack();
	}

	public void generate() {
		if (!(locGen.getLocation().getBlock().getType() == Material.CHEST))
			locGen.getLocation().getBlock().setType(Material.CHEST);
		block = locGen.getLocation().getBlock();
		chest = (Chest) block.getState();
		chestInv = chest.getBlockInventory();
		for (int i = 0; i < itemGen.getChestStack().size(); i++) {
			toAdd = rand.nextInt(itemGen.getChestStack().size());
			chestInv.addItem(itemGen.getChestStack().get(toAdd));
		}
		itemGen.getChestStack().clear();
	}

	public FlexFile getConfigs() {
		return file;
	}

	public RandomItem getItemGen() {
		return itemGen;
	}

	public RandomLoc getLocGen() {
		return locGen;
	}

	public Random getRandom() {
		return rand;
	}

}
