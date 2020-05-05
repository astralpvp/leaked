package me.pyrodamix.kitpvp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public FileConfiguration config = this.getConfig();
	public static Economy economy;
	
	public HashMap<UUID, UUID> duels = new HashMap<UUID,UUID>();
	public HashMap<UUID, UUID> arenas = new HashMap<UUID,UUID>();
	
	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("\n\n Vault has not been found. Plugin disabling.");
			
		}
		
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "KitPvP <3 <3 \n ENABLED");
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		loadConfig();
		loadCommands();
	}
	
	public void onDisable() {
		loadConfig();
	}
	
	private boolean setupEconomy() {
		 if (getServer().getPluginManager().getPlugin("Vault") == null) {
		      return false;
		    }
		    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		    if (rsp == null) {
		      return false;
		    }
		    economy = (Economy)rsp.getProvider();
		    return (economy != null);
	}
	
	private void loadCommands() {
		Commands commands = new Commands();
		getCommand("discord").setExecutor(commands);
		getCommand("pvp").setExecutor(commands);
		getCommand("s2").setExecutor(commands);
		getCommand("s2s").setExecutor(commands);
		getCommand("stats").setExecutor(commands);
		getCommand("duel").setExecutor(commands);
		getCommand("duels").setExecutor(commands);
		
	}
	
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}