package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class onSwapItems implements Listener {

    private Main plugin;

    public onSwapItems(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        if(plugin.cuffed.containsKey(e.getPlayer())){
            e.setCancelled(true);
        }
    }

}