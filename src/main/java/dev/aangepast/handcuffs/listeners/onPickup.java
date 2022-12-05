package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class onPickup implements Listener {

    private Main plugin;

    public onPickup(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){

        Player player = e.getPlayer();

        if(plugin.cuffed.containsKey(player)){

            e.setCancelled(true);
        }
    }

}