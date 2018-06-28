package com.github.aasmus.pvptoggle;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PvPToggleUtil {
	public static boolean getPlayerState(UUID uuid){
		Boolean result = PvPToggle.players.get(uuid);
		if(result==null) return false;
		else return result;
	}
	public static void setPlayerState(UUID uuid,boolean state){
		PvPToggle.players.put(uuid,state);
	}
}
