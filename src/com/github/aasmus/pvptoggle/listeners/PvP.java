package com.github.aasmus.pvptoggle.listeners;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.aasmus.pvptoggle.PvPToggle;
import com.github.aasmus.pvptoggle.PvPToggleUtil;


public class PvP implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	//fired when an entity is hit
	public void onHit(EntityDamageByEntityEvent event) {
		//check if attack was a player
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
		
			Player damager = (Player) event.getDamager(); //player who hit
			boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
			Player attacked = (Player) event.getEntity(); //player who was hit
			boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
			
			if(!damager.getName().equals(attacked.getName())){
				if (damagerState==false) { 
					event.setCancelled(true);
					damager.sendMessage(ChatColor.RED + "You have pvp disabled!");
				}else if (attackedState==false) {
					event.setCancelled(true);
					damager.sendMessage(ChatColor.RED + attacked.getDisplayName() + " has pvp disabled!");
				}
			}
		//checks if damage was done by a projectile
		} else if (event.getDamager() instanceof Projectile) {
			Projectile arrow = (Projectile) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					Player damager = (Player) arrow.getShooter();
					boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
					Player attacked = (Player) event.getEntity();
					boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
					
					if(!damager.getName().equals(attacked.getName())){
						if(damagerState==false) {
							event.setCancelled(true);
							damager.sendMessage(ChatColor.RED + "You have pvp disabled!");
						}else if(attackedState==false) {
							event.setCancelled(true);
							damager.sendMessage(ChatColor.RED + attacked.getDisplayName() + " has pvp disabled!");
						}
					}
				}
			}
		//checks if damage was done by a potion
		} else if(event.getDamager() instanceof ThrownPotion) {
			ThrownPotion potion = (ThrownPotion) event.getDamager();
			if (potion.getShooter() instanceof Player && event.getEntity() instanceof Player) {
				Player damager = (Player) potion.getShooter();
				boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
				Player attacked = (Player) event.getEntity();
				boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
				
				if(!damager.getName().equals(attacked.getName())){
					if (damagerState==false) {
						event.setCancelled(true);
						damager.sendMessage(ChatColor.RED + "You have pvp disabled!");
					}else if (attackedState==false) {
						event.setCancelled(true);
						damager.sendMessage(ChatColor.RED + attacked.getDisplayName() + " has pvp disabled!");
					}
					
				}
				
				
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	//fired when a splash potion is thrown
	public void onPotionSplash(PotionSplashEvent event) {
		if(event.getPotion().getShooter() instanceof Player) {
			   for(LivingEntity entity : event.getAffectedEntities()) {
			        if(entity instanceof Player) {
			    		Player damager = (Player) event.getPotion().getShooter();
			    		boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
			        	Player attacked = (Player) entity;
			    		boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
			    		
			    		if(damager.getName().equals(attacked.getName())==false  &&  
			    				isWithDamagingEffect(event.getEntity().getEffects()) ){
			    			
			    			if(damagerState==false) {
				    			//event.setCancelled(true);
				    			damager.sendMessage(ChatColor.RED + "You have pvp disabled!");
				    		}else if(attackedState==false) {
				    			//event.setCancelled(true);
				    			damager.sendMessage(ChatColor.RED + attacked.getDisplayName() + " has pvp disabled!");
				    		}
			    		}
			    				    		
			        }
			   }
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	//fired when a lingering potion is thrown
	public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
		if(event.getEntity().getShooter() instanceof Player) {
			if(event.getHitEntity() instanceof Player) {
	    		Player damager = (Player) event.getEntity().getShooter();
	    		boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
	        	Player attacked = (Player) event.getHitEntity();
	    		boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
	    		
	    		
	    		
	    		if(damager.getName().equals(attacked.getName())==false  &&  
	    				isWithDamagingEffect(event.getEntity().getEffects()) ){
	    			
	    			  if(damagerState==false) {
	  	    			event.setCancelled(true);
	  	    			damager.sendMessage(ChatColor.RED + "You have pvp disabled!");
	  	    		} else if(attackedState==false) {
	  	    			event.setCancelled(true);
	  	    			damager.sendMessage(ChatColor.RED + attacked.getDisplayName() + " has pvp disabled!");
	  	    		}
	    			  
	    		}
	    		
			}
		}
	}
	

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    //fired when lingering potion cloud is active
    public void onCloudEffects(AreaEffectCloudApplyEvent event) {
    	if(event.getEntity().getSource() instanceof Player) {
    		Iterator<LivingEntity> it = event.getAffectedEntities().iterator();
        	while(it.hasNext()) {
        		LivingEntity entity = it.next();
        		if(entity instanceof Player) {
    	    		Player damager = (Player) event.getEntity().getSource();
    	    		boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
    	        	Player attacked = (Player) entity;
    	    		boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
    	    		
    	    		if(damager.getName().equals(attacked.getName())==false  &&  
    	    				isDamagingPotion(event.getEntity().getBasePotionData().getType().getEffectType()) ){
    	    
    	    			
    	    			if(attackedState==false)
	    	    			it.remove();
	    	    		else if(damagerState==false)
	    	    			it.remove();
    	    		}
    	    		
        		}
        	}
    	}
    }
    
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    //fired when a player uses a fishing rod
    public void onPlayerFishing (final PlayerFishEvent event) {
        final Player damager = event.getPlayer();
        boolean damagerState = PvPToggleUtil.getPlayerState(damager.getUniqueId());
        if (event.getCaught() instanceof Player) {
            final Player attacked = (Player) event.getCaught();
            
            if(!damager.getName().equals(attacked.getName())){
            	boolean attackedState = PvPToggleUtil.getPlayerState(attacked.getUniqueId());
                if (damager.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD || damager.getInventory().getItemInOffHand().getType() == Material.FISHING_ROD) {
        			if (damagerState==false) {
        				event.setCancelled(true);
        				damager.sendMessage(ChatColor.RED + "You have pvp disabled!");
        			} else if (attackedState==false) {
        				event.setCancelled(true);
        				damager.sendMessage(ChatColor.RED + attacked.getDisplayName() + " has pvp disabled!");
        			}
                }
            }
            
        }
    }
    public static boolean isWithDamagingEffect(Collection<PotionEffect> c){
    	for(PotionEffect pe : c){
    		if(isDamagingPotion(pe)){
    			
    			return true;
    		}
    	}
    	return false;
    }
    public static boolean isDamagingPotion(PotionEffectType ptype){
    	if(ptype.equals(PotionEffectType.HARM)) return true;
    	if(ptype.equals(PotionEffectType.POISON)) return true;
    	else return false;
    }
    
    public static boolean isDamagingPotion(PotionEffect pe){
    	if(pe.getType().equals(PotionEffectType.HARM)) return true;
    	if(pe.getType().equals(PotionEffectType.POISON)) return true;
    	else return false;
    }
    
}
