package com.github.flashdaggerx.generator.v1_8;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.flashdaggerx.Sky;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {
	private Generator gen;
	
	private Player p;
	private CommandSender sen;
	private boolean isPlayer;

	private HashMap<String, Integer> tasks;
	private YamlConfiguration loc;

	public Commands() {
		gen = new Generator();
		isPlayer = false;
		tasks = new HashMap<>();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		if (sender instanceof Player) {
			this.sen = sender;
			this.p = (Player) this.sen;
			isPlayer = true;
		} else {
			this.sen = sender;
		}
		try {
			if (cmd.getName().equalsIgnoreCase("skycrates")) {
				if (args.length == 0) {
					sen.sendMessage(ChatColor.RED + "/skycrates <function>\n" + "ref - Refresh the config\n"
							+ "agen (bound) (player) - Spawn a chest at the $(player) location\n"
							+ "rgen (bound) - Spawns a chest within bounds\n"
							+ "cgen (name) (delay) (bound) (world) - Starts generation (per $(delay))\n"
							+ "cgen (name) - Deletes generation task\n" + "cgen - Lists currently active tasks."
							+ "pgen (poolname) (bound) - Generates a chest at pool location\n");
					return true;
				}
				if (args[0].equalsIgnoreCase("ref")) {
					gen.refreshConfiguration();
					sen.sendMessage(ChatColor.YELLOW + "[SkyCrates] Configuration refreshed.");
				} else if (args[0].equalsIgnoreCase("agen")) {
					doAGen(args);
				} else if (args[0].equalsIgnoreCase("rgen")) {
					doRGen(args);
				} else if (args[0].equalsIgnoreCase("cgen")) {
					doCGen(args);
				} else if (args[0].equalsIgnoreCase("pgen")) {
					doPGen(args);
				} else {
					p.sendMessage(ChatColor.RED + "[SkyCrates] Bad Arguments.");
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			sen.sendMessage(ChatColor.RED + "[SkyCrates] " + e);
			return true;
		}
		isPlayer = false;
		p = null;
		sen = null;
		return true;
	}

	private void doAGen(String[] args) {
		Integer bound;
		try {
			bound = Integer.parseInt(args[1]);
			p = Bukkit.getServer().getPlayer(args[2]);
			gen.getLocGen().decideWorld(p.getWorld());
			gen.getLocGen().decideCoordinates(p.getLocation());
			gen.getItemGen().decideItemStack(bound);
			gen.generate();
			p.sendMessage(ChatColor.GREEN + "[SkyCrates] A crate was recieved from " + sen.getName());
		} catch (NumberFormatException | NullPointerException e) {
			sen.sendMessage(ChatColor.RED + "[SkyCrates] (bound) or (player) was null.");
			return;
		}
	}

	private void doRGen(String[] args) {
		Integer bound;
		try {
			bound = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			sen.sendMessage(ChatColor.RED + "[SkyCrates] (bound) was not a number. Defaulting...");
			bound = 1;
		}
		if (isPlayer) {
			gen.getLocGen().decideWorld(p.getWorld());
		} else {
			gen.getLocGen().decideWorld(Bukkit.getServer().getWorlds().get(0));
		}
		gen.getLocGen().decideCoordinates();
		gen.getItemGen().decideItemStack(bound);
		int x = gen.getLocGen().getLocation().getBlockX(),
				y = gen.getLocGen().getLocation().getBlockY(),
				z = gen.getLocGen().getLocation().getBlockZ();
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SkyCrates] Task \"RGen\" "
				+ "Spawned at X" + x + " Y" + y + " Z" + z);
		gen.generate();
	}

	@SuppressWarnings("deprecation")
	private void doCGen(String[] args) {
		String name;
		Integer delay;
		Integer bound;
		World world;
		Integer id;
		if (args.length == 5 && isPlayer) {
			try {
				name = args[1];
				delay = Integer.parseInt(args[2]);
				bound = Integer.parseInt(args[3]);
				world = Bukkit.getServer().getWorld(args[4]);
			} catch (NumberFormatException | NullPointerException e) {
				p.sendMessage("(name), (bound), (delay) or (world) was null. Defaulting...");
				name = ("Task" + System.currentTimeMillis());
				delay = 60;
				bound = 1;
				world = Bukkit.getServer().getWorlds().get(0);
			}
			final String name2 = name;
			final World world2 = world;
			final Integer bound2 = bound;
			final Integer delay2 = delay;
			id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Sky.getInstance(), new BukkitRunnable() {
				@Override
				public void run() {
					try {
						gen.getLocGen().decideWorld(world2);
						gen.getLocGen().decideCoordinates();
						int x = gen.getLocGen().getLocation().getBlockX(),
								y = gen.getLocGen().getLocation().getBlockY(),
								z = gen.getLocGen().getLocation().getBlockZ();
						Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SkyCrates] Task \""
								+ name2 + "\": " + "Spawned at X" + x + " Y" + y + " Z" + z);
					} catch (NullPointerException e) {
						Bukkit.getServer().getConsoleSender()
								.sendMessage(ChatColor.RED + "[SkyCrates] Task #" + this.getTaskId() + " has an NPE.");
						this.cancel();
					}
					gen.getItemGen().decideItemStack(bound2);
					gen.generate();
				}
			}, 0, delay2 * 20);
			sen.sendMessage(ChatColor.GREEN + "[SkyCrates] \"" + name + "\" was created! (Seconds: " + delay + ")");
			tasks.put(name, id);
		} else if (args.length == 4 && isPlayer) {
			try {
				name = args[1];
				delay = Integer.parseInt(args[2]);
				bound = Integer.parseInt(args[3]);
			} catch (NumberFormatException | NullPointerException e) {
				p.sendMessage("(name), (bound), (delay) or (world) was null. Defaulting...");
				name = ("Task" + System.currentTimeMillis());
				delay = 60;
				bound = 1;
			}
			gen.refreshConfiguration();
			p.sendMessage(ChatColor.YELLOW + "[SkyCrates] Configuration refreshed for pool lists.");
			loc = gen.getConfigs().getPoolList();
			if (!(loc.getStringList("pool.locations." + name).isEmpty())) {
				final String name2 = name;
				final Integer bound2 = bound;
				final Integer delay2 = delay;
				id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Sky.getInstance(), new BukkitRunnable() {
					@Override
					public void run() {
						try {
							gen.getLocGen().decideCoordinatesAndWorld(name2);
						} catch (NullPointerException e) {
							Bukkit.getServer().getConsoleSender().sendMessage(
									ChatColor.RED + "[SkyCrates] Task \"" + this.getTaskId() + "\": has an NPE.");
							this.cancel();
						}
						gen.getItemGen().decideItemStack(bound2);
						gen.generate();
					}
				}, 0, delay2 * 20);
				sen.sendMessage(ChatColor.GREEN + "[SkyCrates] \"" + name + "\" was created! (Seconds: " + delay + ")");
				tasks.put(name, id);
			}
		} else if (args.length == 2) {
			name = args[1];
			if (tasks.containsKey(name)) {
				Bukkit.getScheduler().cancelTask(tasks.get(name));
				tasks.remove(name);
				sen.sendMessage(ChatColor.GREEN + "[SkyCrates] \"" + name + "\" has been deleted!");
			} else {
				sen.sendMessage(ChatColor.RED + "[SkyCrates] \"" + name + "\" doesn't exist.");
			}
		} else if (args.length == 1) {
			sen.sendMessage(ChatColor.YELLOW + "[SkyCrates] Active genration tasks:");
			if (!tasks.isEmpty()) {
				sen.sendMessage(ChatColor.YELLOW + tasks.keySet().toString());
				sen.sendMessage(ChatColor.YELLOW + tasks.values().toString());
			} else {
				sen.sendMessage(ChatColor.RED + "None.");
			}
		}
	}

	private void doPGen(String[] args) {
		String name = args[1];
		Integer bound;
		try {
			bound = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			sen.sendMessage(ChatColor.RED + "[SkyCrates] (bound) wasn't a number. Defaulting...");
			bound = 10;
		}
		gen.getLocGen().decideCoordinatesAndWorld(name);
		gen.getItemGen().decideItemStack(bound);
		gen.generate();
	}

}
