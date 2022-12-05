package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onFallDamage implements Listener {

    private Main plugin;

    public onFallDamage(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void fallDamage(EntityDamageEvent e){

        if(e.getEntity() instanceof Player){

            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){

                Player player = (Player) e.getEntity();

                if(plugin.cuffed.containsKey(player)){

                    e.setCancelled(true);
                }
            }
        }
    }

}