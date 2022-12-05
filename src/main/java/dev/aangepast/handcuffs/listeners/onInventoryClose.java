package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import dev.aangepast.handcuffs.components.playerGame;
import dev.aangepast.handcuffs.utils.gameUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class onInventoryClose implements Listener {

    private Main plugin;

    public onInventoryClose(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(ChatColor.RED + "Escape the handcuffs") || e.getView().getTitle().equals(ChatColor.RED + "Escape the zipties")) {

            Player player = (Player) e.getPlayer();

            gameUtil gameUtil = new gameUtil();

            playerGame pGame = gameUtil.getPlayerGame(player, plugin);

            plugin.playerGames.remove(pGame);

        }
    }
}
