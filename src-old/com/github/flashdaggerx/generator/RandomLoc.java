package com.github.flashdaggerx.generator;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import net.md_5.bungee.api.ChatColor;

public class RandomLoc {
	private FlexFile file;

	private int x, y, z, decideNeg;
	private Random rand;

	private World world;
	private Location loc;
	private List<String> locList;

	public RandomLoc(FlexFile file, Random rand) {
		this.file = file;
		this.rand = rand;
		this.world = Bukkit.getServer().getWorlds().get(0);
	}

	public void decideWorld(World w) {
		world = w;
	}

	public void decideCoordinates() {
		x = rand.nextInt(file.getX_max());
		z = rand.nextInt(file.getZ_max());
		decideNeg = rand.nextInt(3);
		switch (decideNeg) {
		case 0:
			{
				x = -x;
				break;
			}
		case 1:
			{
				z = -z;
				break;
			}
		case 2:
			{
				x = -x;
				z = -z;
				break;
			}
		default:
			break;
		}
		y = world.getHighestBlockYAt(x, y);
		loc = new Location(world, x, y, z);
	}

	public void decideCoordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		loc = new Location(world, x, y, z);
	}

	public void decideCoordinates(Location loc) {
		this.loc = loc;
	}

	public void decideCoordinatesAndWorld(String poolName) {
		locList = file.getPoolList().getStringList("pool.locations." + poolName);
		try {
			world = Bukkit.getServer().getWorld(locList.get(0).toString());
			x = Integer.parseInt(locList.get(1).toString());
			y = Integer.parseInt(locList.get(2).toString());
			z = Integer.parseInt(locList.get(3).toString());
		} catch (NumberFormatException e1) {
			Bukkit.getServer().getConsoleSender().sendMessage(
					ChatColor.RED + "[SkyCrates] The location pool " + poolName + "contained a non-number.");
			return;
		}
		this.loc = new Location(world, x, y, z);

	}

	@SuppressWarnings("unused")
	private void printAvailableLists() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Printing List: ");
		for (int i = 0; i < locList.size(); i++) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.YELLOW + "Printing " + i + ": " + locList.get(i));
		}
	}

	public Location getLocation() {
		return loc;
	}
}
