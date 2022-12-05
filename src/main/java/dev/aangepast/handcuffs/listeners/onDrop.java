package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onDrop implements Listener {

    private Main plugin;

    public onDrop(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){

        if(plugin.cuffed.containsKey(e.getPlayer())){

            e.setCancelled(true);
        }
    }


}