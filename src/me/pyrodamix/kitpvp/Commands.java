package me.pyrodamix.kitpvp;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.vault.economy.Economy;

public class Commands implements CommandExecutor {
	/**
	 * Terrible code.
	 */
	public Main plugin = Main.getPlugin(Main.class);
	@SuppressWarnings("unused")
	private Economy economy = Main.economy;
	public FileConfiguration config = plugin.getConfig();
	
	public void giveKit(Player player, String kit) {
		final Player p = player;
		final String k = kit;
		long time = System.currentTimeMillis();
		long cooldown = config.getLong("Kit." + k + ".cooldown");
		long lastUse = config.getLong("Player." + p.getUniqueId() + ".cooldowns." + k);
		long remaining = (cooldown + lastUse) - time;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(remaining);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remaining);
        long hours = TimeUnit.MILLISECONDS.toHours(remaining);
        
		if (time > cooldown + lastUse) {
			pvpKit(p);
			p.sendMessage(ChatColor.GREEN + "You have recieved the " + k + " kit.");
			config.set("Player." + p.getUniqueId() + ".cooldowns." + k, time);
			plugin.saveConfig();
		} else {
			if (remaining <= 999) {
				player.sendMessage(ChatColor.RED + "You must wait " + remaining + " milliseconds to use this kit again.");
				return;
			}
			if (seconds <= 59 && seconds > 0) {
				player.sendMessage(ChatColor.RED + "You must wait " + seconds + " seconds to use this kit again.");
				return;
			}
			if (minutes <= 59 && minutes > 0) {
				player.sendMessage(ChatColor.RED + "You must wait " + minutes + " minutes and " + seconds + " seconds to use this kit again.");
				return;
			}
			if (hours == 1 && minutes <= 59 && minutes > 0) {
				player.sendMessage(ChatColor.RED + "You must wait " + hours + " hour and " + minutes + " minutes to use this kit again.");
				return;
			}
			if (hours > 1 && minutes <= 59 && minutes > 0) {
				player.sendMessage(ChatColor.RED + "You must wait " + hours + " hours and " + minutes + " minutes to use this kit again.");
				return;
			}
		}
	}
	
	public void pvpKit(Player player) {
		Player p = player;
		
		ItemStack pvpSword = new ItemStack(Material.DIAMOND_SWORD);
		pvpSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		pvpSword.addEnchantment(Enchantment.DURABILITY, 3);
		
		
		ItemStack pvpHelm = new ItemStack(Material.DIAMOND_HELMET);
		pvpHelm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		pvpHelm.addEnchantment(Enchantment.DURABILITY, 3);
		
		ItemStack pvpChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
		pvpChest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		pvpChest.addEnchantment(Enchantment.DURABILITY, 3);
		
		ItemStack pvpPants = new ItemStack(Material.DIAMOND_LEGGINGS);
		pvpPants.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		pvpPants.addEnchantment(Enchantment.DURABILITY, 3);
		
		ItemStack pvpBoots = new ItemStack(Material.DIAMOND_BOOTS);
		pvpBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		pvpBoots.addEnchantment(Enchantment.DURABILITY, 3);

		ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 4);
		ItemStack healthPot = new ItemStack(Material.POTION, 1, (short) 16421);
		ItemStack speedPot = new ItemStack(Material.POTION, 1, (short) 8226);
		
		p.getInventory().addItem(pvpSword);
		p.getInventory().addItem(pearl);
		p.getInventory().addItem(pvpHelm);
		p.getInventory().addItem(pvpChest);
		p.getInventory().addItem(pvpPants);
		p.getInventory().addItem(pvpBoots);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(speedPot); //l4
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(speedPot); //l1
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(speedPot); //l2
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(healthPot);
		p.getInventory().addItem(speedPot); //l3
		return;
	}
	
	public void s2Kit(Player player) {
		Player p = player;
		
		ItemStack pvpSword = new ItemStack(Material.DIAMOND_SWORD);
		pvpSword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		pvpSword.addEnchantment(Enchantment.DURABILITY, 3);

		p.getInventory().addItem(pvpSword);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("discord")) {
				player.sendMessage(ChatColor.YELLOW + ">> https://discord.gg/mwk3CjJ");
				return true;
			}
			
			if (cmd.getName().equalsIgnoreCase("pvp")) {
					giveKit(player, "pvp");
					return true;
				}
			
			if (cmd.getName().equalsIgnoreCase("s2") && player.hasPermission("kitpvp.s2")) {
				if (config.getInt("Player." + player.getUniqueId() + ".s2") > 0) {
					config.set("Player." + player.getUniqueId() + ".s2", config.getInt("Player." + player.getUniqueId() + ".s2") -1);
					s2Kit(player);
					player.sendMessage(ChatColor.GREEN + "An s2 was delivered to your inventory.");
					return true;
				}
				player.sendMessage(ChatColor.RED + "You have no s2 to claim!");
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("s2s") && player.hasPermission("kitpvp.s2")) {
				if (config.getInt("Player." + player.getUniqueId() + ".s2") == 1) {
					player.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.GREEN + config.getInt("Player." + player.getUniqueId() + ".s2") + ChatColor.YELLOW + " s2 saved");
					return true;
				}
				player.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.GREEN + config.getInt("Player." + player.getUniqueId() + ".s2") + ChatColor.YELLOW + " s2s saved");
				return true;
			}
			
			if (cmd.getName().equalsIgnoreCase("stats")) {
				if (args.length == 0) {
					int kills = config.getInt("Player." + player.getUniqueId() + ".Stats.Kills");
					int deaths = config.getInt("Player." + player.getUniqueId() + ".Stats.Deaths");
					int ks = config.getInt("Player." + player.getUniqueId() + ".Stats.KillStreak");
					int highestKS = config.getInt("Player." + player.getUniqueId() + ".Stats.Highest KillStreak");
					int KDR = config.getInt("Player." + player.getUniqueId() + ".Stats.KDR");
					//
					if (kills != 0 && deaths != 0) {
						KDR = kills / deaths;
					}
					player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Your Stats" + ChatColor.DARK_GRAY + "]");
					player.sendMessage(ChatColor.YELLOW + "Kills: " + ChatColor.AQUA + kills);
					player.sendMessage(ChatColor.YELLOW + "Deaths: " + ChatColor.AQUA + deaths);
					player.sendMessage(ChatColor.YELLOW + "KillStreak: " + ChatColor.AQUA + ks);
					player.sendMessage(ChatColor.YELLOW + "Highest KillStreak: " + ChatColor.AQUA + highestKS);
					player.sendMessage(ChatColor.YELLOW + "KDR: " + ChatColor.AQUA + KDR);
					return true;
				}
				if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (target != player) {
						int tkills = config.getInt("Player." + target.getUniqueId() + ".Stats.Kills");
						int tdeaths = config.getInt("Player." + target.getUniqueId() + ".Stats.Deaths");
						int tks = config.getInt("Player." + target.getUniqueId() + ".Stats.KillStreak");
						int thighestKS = config.getInt("Player." + target.getUniqueId() + ".Stats.Highest KillStreak");
						int tKDR = config.getInt("Player." + target.getUniqueId() + ".Stats.KDR");
						//
						if (tkills != 0 && tdeaths != 0) {
							tKDR = tkills / tdeaths;
						}
						player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + target.getName() + "'s Stats" + ChatColor.DARK_GRAY + "]");
						player.sendMessage(ChatColor.YELLOW + "Kills: " + ChatColor.AQUA + tkills);
						player.sendMessage(ChatColor.YELLOW + "Deaths: " + ChatColor.AQUA + tdeaths);
						player.sendMessage(ChatColor.YELLOW + "KillStreak: " + ChatColor.AQUA + tks);
						player.sendMessage(ChatColor.YELLOW + "Highest KillStreak: " + ChatColor.AQUA + thighestKS);
						player.sendMessage(ChatColor.YELLOW + "KDR: " + ChatColor.AQUA + tKDR);
						return true;
						}
						player.sendMessage(ChatColor.RED + "Just do '/stats' xD");
						return true;
					}
					player.sendMessage(ChatColor.RED + "That player is not online.");
					return true;
				}
			}
			//
			if (cmd.getName().equalsIgnoreCase("duel") && player.hasPermission("kitpvp.duel")) {
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "/duel <player>");
					return true;
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("c")) {
						if (plugin.duels.containsKey(player.getUniqueId())) {
						plugin.duels.remove(player.getUniqueId());
						player.sendMessage(ChatColor.YELLOW + "Duel request cancelled");
						return true;
						}
						player.sendMessage(ChatColor.RED + "You haven't requested anyone to duel");
						return true;
					}
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (target != player) {
							if (!plugin.duels.containsKey(player.getUniqueId())) {
							player.sendMessage(ChatColor.YELLOW + "You have requested to duel " + target.getName());
							target.sendMessage(ChatColor.YELLOW + "You have been requested to duel by " + player.getName());
							plugin.duels.put(player.getUniqueId(), target.getUniqueId());
							return true;
							}
							player.sendMessage(ChatColor.RED + "You have already requested someone to duel. Do " + ChatColor.YELLOW + "/duel c" + ChatColor.RED + " to cancel it and try again.");
							return true;
						}
					}
					player.sendMessage(ChatColor.RED + "That player is not online.");
					return true;
				}
				
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("accept")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							if (!plugin.arenas.containsKey(player.getUniqueId())) {
								if (plugin.duels.get(target.getUniqueId()) == player.getUniqueId()) {
									
									player.teleport((Location) config.get("Duels.1"));
									player.sendMessage("teleporting");
									target.teleport((Location) config.get("Duels.2"));
									target.sendMessage("teleporting");
									
									plugin.arenas.put(player.getUniqueId(), target.getUniqueId());
									plugin.duels.remove(player.getUniqueId());
									plugin.duels.remove(target.getUniqueId());
									return true;
								}
								player.sendMessage(ChatColor.RED + "You don't have a duel request");
								return true;
							}
							player.sendMessage(ChatColor.RED + "You are already in an arena.");
							return true;
						}
						player.sendMessage(ChatColor.RED + "That player is not online");
						return true;
					}
				}
			}
			//
			if (cmd.getName().equalsIgnoreCase("duels") && player.hasPermission("kitpvp.duels")) {
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("setarena")) {
						if (!(config.contains("Duels." + args[1]))) {
							config.addDefault("Duels." + args[1], player.getLocation());
							player.sendMessage(ChatColor.YELLOW + "Set arena " + args[1]);
							plugin.saveConfig();
							return true;
							}
							player.sendMessage(ChatColor.RED + "That is already an arena name.");
							return true;
					}
					if (args[0].equalsIgnoreCase("delarena")) {
						if (config.contains("Duels." + args[1])) {
							config.set("Duels." + args[1], null);
							player.sendMessage(ChatColor.YELLOW + "Arena " + args[1] + " removed");
							plugin.saveConfig();
							return true;
						}
						player.sendMessage(ChatColor.RED + "That arena does not exist");
						return true;
					}
					player.sendMessage(ChatColor.YELLOW + "/duels delarena <arenaName>");
					return true;
				}
				player.sendMessage(ChatColor.YELLOW + "/duels setarena <arenaName>");
				return true;
			}
		}
		return true;
	}
}
