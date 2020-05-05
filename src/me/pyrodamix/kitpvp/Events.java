package me.pyrodamix.kitpvp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.milkbowl.vault.economy.Economy;

public class Events implements Listener {
	
	public HashMap<UUID, UUID> spam = new HashMap<UUID, UUID>();
	
	public Main plugin = Main.getPlugin(Main.class);
	public FileConfiguration config = plugin.getConfig();
	private Economy economy = Main.economy;
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!config.contains("Player." + player.getUniqueId())) {
		config.addDefault("Player." + player.getUniqueId() + ".Name", player.getName().toString());
		config.addDefault("Player." + player.getUniqueId() + ".Stats" + ".Kills", 0);
		config.addDefault("Player." + player.getUniqueId() + ".Stats" + ".Deaths", 0);
		config.addDefault("Player." + player.getUniqueId() + ".Stats" + ".KillStreak", 0);
		config.addDefault("Player." + player.getUniqueId() + ".Stats" + ".Highest KillStreak", 0);
		config.addDefault("Player." + player.getUniqueId() + ".Stats" + ".KDR", 0);
		config.addDefault("Player." + player.getUniqueId() + ".OwnedKits", "pvp");
		plugin.saveConfig();
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		//MESSAGE
		Player killed = e.getEntity();
		Player killer = killed.getKiller();
		int killerHealth = (int) killer.getHealth();
		int killReward = config.getInt("Kill Reward");
		//STAT CHANGES
		int ks = config.getInt("Player." + killer.getUniqueId() + ".Stats.KillStreak");
		int highestKS = config.getInt("Player." + killer.getUniqueId() + ".Stats.Highest KillStreak");
		//
		int tks = config.getInt("Player." + killed.getUniqueId() + ".Stats.KillStreak");
		int thighestKS = config.getInt("Player." + killed.getUniqueId() + ".Stats.Highest KillStreak");
		
		//killerKills, killerKillStreak change
		config.set("Player." + killer.getUniqueId() + ".Stats.Kills", config.getInt("Player." + killer.getUniqueId() + ".Stats.Kills") + 1);
		
		config.set("Player." + killer.getUniqueId() + ".Stats.KillStreak", config.getInt("Player." + killer.getUniqueId() + ".Stats.KillStreak") +1);
		//killedDeath change
		config.set("Player." + killed.getUniqueId() + ".Stats.Deaths", config.getInt("Player." + killed.getUniqueId() + ".Stats.Deaths") + 1);
		//
		if (ks > highestKS) {
			config.set("Player." + killer.getUniqueId() + ".Stats.Highest KillStreak", ks+1);
		}
		if (tks > thighestKS) {
			config.set("Player." + killer.getUniqueId() + ".Stats.Highest KillStreak", tks+1);
		}
		config.set("Player." + killed.getUniqueId() + ".Stats.KillStreak", 0);
		//
		killed.sendMessage(ChatColor.RED + "You died to " + ChatColor.DARK_RED + killer.getName() + ChatColor.RED + " and they had " + killerHealth /2 + " hearts");		
		killer.sendMessage(ChatColor.YELLOW + "You killed " + ChatColor.GREEN +  killed.getName() + ChatColor.YELLOW + " and " + ChatColor.GREEN + "$" + killReward + ChatColor.YELLOW + " has been added to your account.");
		
		economy.depositPlayer(killer, killReward);
		this.plugin.saveConfig();
		
		//s2 on kill
		if (killer.hasPermission("kitpvp.s2")) {
			if (!config.contains("Player." + killer.getUniqueId() + ".s2")) {
				config.addDefault("Player." + killer.getUniqueId() + ".s2", 0);
				plugin.saveConfig();
			}
			config.set("Player." + killer.getUniqueId() + ".s2", config.getInt("Player." + killer.getUniqueId() + ".s2") +1);
			killer.sendMessage(ChatColor.GREEN + "An s2 has been added to your collection");
			plugin.saveConfig();
		}
	}
}
