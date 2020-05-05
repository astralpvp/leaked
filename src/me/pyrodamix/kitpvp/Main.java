package me.pyrodamix.kitpvp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener
{

	public FileConfiguration config = this.getConfig();
	public static Economy economy;
	
	public HashMap<UUID, UUID> duels = new HashMap<UUID,UUID>();
	public HashMap<UUID, UUID> arenas = new HashMap<UUID,UUID>();
	
	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("\n\n Vault has not been found. Plugin disabling.");
			
		}
		
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "KitPvP <3 <3 \n ENABLED");
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "SHIT PLUGIN");
		getServer().getPluginManager().registerEvents(new Events(), this);
		getServer().getPluginManager().registerEvents(this, this);
		loadConfig();
		loadCommands();
		
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				for (Player player : Bukkit.getOnlinePlayers())
				{
					updateScoreboard(player);
				}
			}
		}.runTaskTimer(this, 2L, 2L);
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
	
    private void createScoreboard(Player player) {
        ScoreHelper helper = ScoreHelper.createScore(player);
        helper.setTitle("&e    &e-= &6Astral PvP &e=-    &e");
        
        int KDR = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.KDR");
		//
       int kills = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.Kills");
       int deaths = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.Deaths");
       int KS = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.KillStreak");
       int highestKS = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.Highest KillStreak");
		if (kills != 0 && deaths != 0) {
			KDR = kills / deaths;
		}
        
        
        helper.setSlot(9, "  &e&m---------------  &c");
        helper.setSlot(8, "&6Kills: &e" + kills);
        helper.setSlot(7, "&6Deaths: &e" + deaths);
        helper.setSlot(6, "&6KDR: &e" + KDR);
        helper.setSlot(5, "  &e&m---------------  &c");
        helper.setSlot(4, "&6KS: &e" + KS);
        helper.setSlot(3, "&6Highest KS: &e" + highestKS);
        helper.setSlot(2, "  &e&m---------------  &c");
        helper.setSlot(1, "&e  &e   astralpvp.org  &c");
    }

    private void updateScoreboard(Player player) {
        if(ScoreHelper.hasScore(player)) {
            ScoreHelper helper = ScoreHelper.getByPlayer(player);
            helper.setTitle("&e    &e-= &6Astral PvP &e=-    &e");
            
            int KDR = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.KDR");
    		//
           int kills = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.Kills");
           int deaths = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.Deaths");
           int KS = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.KillStreak");
           int highestKS = Main.getPlugin(Main.class).getConfig().getInt("Player." + player.getUniqueId() + ".Stats.Highest KillStreak");
    		if (kills != 0 && deaths != 0) {
    			KDR = kills / deaths;
    		}
            
            
            helper.setSlot(9, "  &e&m---------------  &c");
            helper.setSlot(8, "&6Kills: &e" + kills);
            helper.setSlot(7, "&6Deaths: &e" + deaths);
            helper.setSlot(6, "&6KDR: &e" + KDR);
            helper.setSlot(5, "  &e&m---------------  &c");
            helper.setSlot(4, "&6KS: &e" + KS);
            helper.setSlot(3, "&6Highest KS: &e" + highestKS);
            helper.setSlot(2, "  &e&m---------------  &c");
            helper.setSlot(1, "&e  &e   astralpvp.org  &c");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createScoreboard(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(ScoreHelper.hasScore(player)) {
            ScoreHelper.removeScore(player);
        }
    }
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}