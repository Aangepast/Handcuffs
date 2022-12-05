package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import dev.aangepast.handcuffs.utils.gameUtil;
import dev.aangepast.handcuffs.components.playerGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class onInventoryInteract implements Listener {

    private Main plugin;

    public onInventoryInteract(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){

        Player player = (Player) e.getWhoClicked();

        if(plugin.cuffed.containsKey(player)){

            if(player.getOpenInventory().getTitle().equals(ChatColor.RED + "Escape the handcuffs")){

                if(!(e.getRawSlot() < 18) || e.getRawSlot() == -999){player.sendMessage(ChatColor.RED+"You currently cannot interact with this slot!");e.setCancelled(true);return;}

                gameUtil gameUtil = new gameUtil();
                playerGame pGame = gameUtil.getPlayerGame(player, plugin);

                int clickedSlot = e.getRawSlot();

                if(!pGame.getStarted()){player.playSound(player.getLocation(), "entity.villager.no",1,1);player.sendMessage(ChatColor.RED+"The game has not been started yet!");e.setCancelled(true);return;}

                if(pGame.getLastItem() != null) {

                    if (pGame.getChosenItems().get(e.getRawSlot()) == pGame.getLastItem()) {

                        if(e.getInventory().getItem(clickedSlot).getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Your guess")){
                            e.setCancelled(true);
                            return;
                        }

                        // Player has found the correct item

                        ItemStack newItem = pGame.getChosenItems().get(clickedSlot);

                        ItemMeta newItemMeta = newItem.getItemMeta();
                        newItemMeta.setDisplayName(ChatColor.WHITE + "Your guess");
                        newItem.setItemMeta(newItemMeta);

                        e.getInventory().setItem(clickedSlot, newItem);
                        player.playSound(player.getLocation(), "entity.player.levelup", 1, 1);
                        pGame.setLastItem(null);
                        gameUtil.showAllItems(player, e.getInventory(), plugin, pGame);

                    } else if (e.getInventory().getItem(clickedSlot).getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Your guess")){
                        e.setCancelled(true);
                        return;

                    } else {
                        player.closeInventory();
                        player.playSound(player.getLocation(), "entity.villager.no", 1, 1);
                        player.sendMessage(ChatColor.RED + "You tried to free yourself, but it didn't work out as intended.");
                    }

                } else {

                    ItemStack newItem = pGame.getChosenItems().get(clickedSlot);

                    ItemMeta newItemMeta = newItem.getItemMeta();

                    newItemMeta.setDisplayName(ChatColor.WHITE + "Your guess");
                    newItem.setItemMeta(newItemMeta);

                    e.getInventory().setItem(clickedSlot, newItem);

                    pGame.setLastItem(pGame.getChosenItems().get(clickedSlot));

                }

            }

            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onInvMove(InventoryMoveItemEvent e){

        Player player = (Player) e.getInitiator().getHolder();

        if(plugin.cuffed.containsKey(player)){

            e.setCancelled(true);
        }
    }

}