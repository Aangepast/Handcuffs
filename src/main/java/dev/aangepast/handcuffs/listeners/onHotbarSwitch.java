package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class onHotbarSwitch implements Listener {

    private Main plugin;

    public onHotbarSwitch(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void hotbarSwitch(PlayerItemHeldEvent e){

        if(plugin.cuffed.containsKey(e.getPlayer())){

            e.setCancelled(true);
        }
    }

}