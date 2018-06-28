package com.github.aasmus.pvptoggle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PvPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) { //check if command sender is console
			Player other = Bukkit.getPlayerExact(args[1]);
			if(other == null) { //make sure the player is online
			    sender.sendMessage(ChatColor.RED + "Could not find a player by the name " + args[1]);
			} else { //set pvp state
				boolean current = PvPToggleUtil.getPlayerState(other.getUniqueId());
				if(args[0].equals("toggle")) {
					PvPToggleUtil.setPlayerState(other.getUniqueId(),!current);
				} else if(args[0].equalsIgnoreCase("on")) {
					PvPToggleUtil.setPlayerState(other.getUniqueId(),true);
				} else if(args[0].equalsIgnoreCase("off")) {
					PvPToggleUtil.setPlayerState(other.getUniqueId(),false);
				}
				current = PvPToggleUtil.getPlayerState(other.getUniqueId());
				sender.sendMessage(ChatColor.GREEN + other.getDisplayName() + "'s pvp state has been changed to " + current + ".");
			}
		} else if(sender instanceof Player) { //check if command sender is player
			if(cmd.getName().equalsIgnoreCase("pvp")) {
				Player p = (Player) sender;
				if(args.length == 0) {
					boolean current = PvPToggleUtil.getPlayerState(p.getUniqueId());
					PvPToggleUtil.setPlayerState(p.getUniqueId(),!current);
				
				} else if(args.length == 1) {
					boolean current = PvPToggleUtil.getPlayerState(p.getUniqueId());
					if(args[0].equals("toggle")) {
						PvPToggleUtil.setPlayerState(p.getUniqueId(),!current);
					} else if(args[0].equalsIgnoreCase("on")) {
						PvPToggleUtil.setPlayerState(p.getUniqueId(),true);
					} else if(args[0].equalsIgnoreCase("off")) {
						PvPToggleUtil.setPlayerState(p.getUniqueId(),false);
					} else if(args[0].equalsIgnoreCase("status")) {
						sender.sendMessage(ChatColor.GREEN + "Your pvp set to " + current + ".");
					}
				} else if(args.length == 2) {
					if(sender.hasPermission("pvptoggle.others.set")) {
						Player other = Bukkit.getPlayerExact(args[1]);
						if(other == null) {
						    sender.sendMessage(ChatColor.RED + "Could not find a player by the name " + args[1]);
						} else {
							boolean current = PvPToggleUtil.getPlayerState(other.getUniqueId());
							if(args[0].equals("toggle")) {
								PvPToggleUtil.setPlayerState(p.getUniqueId(),!current);
							} else if(args[0].equalsIgnoreCase("on")) {
								PvPToggleUtil.setPlayerState(p.getUniqueId(),true);
							} else if(args[0].equalsIgnoreCase("off")) {
								PvPToggleUtil.setPlayerState(p.getUniqueId(),false);
							}
							current = PvPToggleUtil.getPlayerState(other.getUniqueId());
							sender.sendMessage(ChatColor.GREEN + other.getDisplayName() + "'s pvp state has been changed to " + current + ".");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to set others pvp  state.");
					}
				}
				
				boolean finalState = PvPToggleUtil.getPlayerState(p.getUniqueId());
				if(finalState){
					p.sendMessage(ChatColor.GREEN + "Your pvp has been enabled!");
					
				}
				else{
					p.sendMessage(ChatColor.GREEN + "Your pvp has been disabled!");
					
				}
			}
			return true;
		}
		return false;
	}
}
