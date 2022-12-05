package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onPlayerAttack implements Listener {

    private Main plugin;

    public onPlayerAttack(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){

        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){

            Player player = (Player) e.getDamager();

            if(plugin.cuffed.containsKey(player)){

                e.setCancelled(true);

            }
        }

    }

}