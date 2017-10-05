package com.github.flashdaggerx.generator.v1_11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;

import com.github.flashdaggerx.generator.FlexFile;

import net.minecraft.server.v1_11_R1.Item;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.MinecraftKey;

public class RandomItem {
	private FlexFile file;

	private int size, delay;
	private Random rand;

	private String id;
	private MinecraftKey key;

	private List<String> poolStack;

	private List<CraftItemStack> stack;
	private CraftItemStack craftItem;
	private Item toItem;

	private boolean isInit;

	public RandomItem(FlexFile file, Random rand) {
		this.file = file;
		this.rand = rand;
		this.stack = new ArrayList<>();
		this.isInit = false;
		refreshPoolStack();
	}

	public void refreshPoolStack() {
		if (isInit)
			poolStack.clear();
		poolStack = file.getPoolList().getStringList("pool.get");
		isInit = true;
	}
	
	public void decideItemPool(String lootpool) {
		poolStack = file.getPoolList().getStringList("pool." + lootpool);
	}

	public void decideItemStack(int bound) {
		for (int i = 0; i < poolStack.size(); i++) {
			id = poolStack.get(i);
			size = rand.nextInt(bound);
			if (id != null) {
				key = new MinecraftKey(id);
				toItem = Item.REGISTRY.get(key);
				if (toItem != null) {
					craftItem = CraftItemStack.asCraftMirror(new ItemStack(toItem, size));
					stack.add(craftItem);
				}
			}
		}
	}

	public void decideItemStack(String[] items, int bound) {
		for (int i = 0; i < items.length; i++) {
			id = items[i];
			size = rand.nextInt(bound);
			if (id != null) {
				key = new MinecraftKey(id);
				toItem = Item.REGISTRY.get(key);
				if (toItem != null) {
					craftItem = CraftItemStack.asCraftMirror(new ItemStack(toItem, size));
					stack.add(craftItem);
				}
			}
		}
	}

	public void addToItemStack(String item, int bound) {
		id = item;
		size = rand.nextInt(bound);
		if (id != null) {
			key = new MinecraftKey(id);
			toItem = Item.REGISTRY.get(key);
			if (toItem != null) {
				craftItem = CraftItemStack.asCraftMirror(new ItemStack(toItem, size));
				stack.add(craftItem);
			}
		}
	}

	public List<CraftItemStack> getChestStack() {
		return stack;
	}

	public int getDelay(String poolName) {
		delay = Integer.parseInt(file.getPoolList().getStringList("pool.locations." + poolName).get(4));
		return delay;
	}
	
	public String getItemPool(String lootpool) {
		return file.getPoolList().getStringList("pool.locations." + lootpool).get(5);
	}
}
