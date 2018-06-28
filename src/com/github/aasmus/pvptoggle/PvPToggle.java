package com.github.aasmus.pvptoggle;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.aasmus.pvptoggle.listeners.PlayerJoin;
import com.github.aasmus.pvptoggle.listeners.PlayerLeave;
import com.github.aasmus.pvptoggle.listeners.PvP;

public class PvPToggle extends JavaPlugin implements Listener {
	public FileConfiguration config = getConfig();
	public static HashMap<UUID,Boolean> players = new HashMap<UUID,Boolean>();
	
	@Override
	public void onEnable() {
		//register events
		getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
		Bukkit.getPluginManager().registerEvents(new PvP(), this);
		//register command
		this.getCommand("pvp").setExecutor(new PvPCommand());
	}

    @Override
    public void onDisable() {
    	
    }

}
