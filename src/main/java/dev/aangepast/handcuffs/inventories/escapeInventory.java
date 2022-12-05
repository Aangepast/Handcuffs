package dev.aangepast.handcuffs.inventories;

import dev.aangepast.handcuffs.Main;
import dev.aangepast.handcuffs.components.playerGame;
import dev.aangepast.handcuffs.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class escapeInventory {

    ItemStack grayGlass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
    ItemStack lightGrayGlass = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();

    public escapeInventory(Player player, Main plugin){

        Inventory inv = Bukkit.createInventory(player, 18, ChatColor.RED + "Escape the handcuffs");

        playerGame pGame = new playerGame();
        pGame.setPlayer(player);
        pGame.setStarted(false);

        for(int i=0;i<2;i++){
            pGame.addAllPlayerItems(plugin.items);
        }

        //random item selector & saver & placer
        Random random = new Random();

        for (int i=0;i<18;i++) {
            ItemStack randomItem = pGame.getPlayerItems().get(random.nextInt(pGame.getPlayerItems().size()));
            ItemMeta randomItemMeta = randomItem.getItemMeta();
            randomItemMeta.setDisplayName(ChatColor.RESET + " ");
            randomItem.setItemMeta(randomItemMeta);
            pGame.addChosenItems(randomItem);
            pGame.removePlayerItems(randomItem);
            inv.setItem(i, randomItem);
        }

        plugin.playerGames.add(pGame);

        player.openInventory(inv);

        playStartingSound(player, plugin, 3, 20);

        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
            public void run(){
                playStartingAnimationLightGray(plugin, inv, 0, 17);

                plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
                    public void run(){
                        playStartingAnimationGray(player, plugin, inv, 0, 17, pGame);
                    }
                }, 2);


            }
        },20*4);

    }

    public void playStartingSound(Player player, Main plugin, int times, int delay){
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
            public void run(){

                if(!player.getOpenInventory().getTitle().equals(ChatColor.RED + "Escape the handcuffs")){return;}

                player.playSound(player.getLocation(), "block.stone_button.click_off", 1, 1);

                if(times >0){
                    playStartingSound(player, plugin, times - 1, delay);
                }
            }
        },delay);
    }

    public void playStartingAnimationGray(Player player, Main plugin, Inventory inv, int beginningNumber, int times,playerGame pGame){
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
            public void run(){
                if(!player.getOpenInventory().getTitle().equals(ChatColor.RED + "Escape the handcuffs")){return;}
                inv.setItem(beginningNumber,grayGlass);
                player.playSound(player.getLocation(), "entity.item.pickup", 1, 1);
                if(times > 0){
                    playStartingAnimationGray(player, plugin, inv, beginningNumber+1, times-1, pGame);
                } else {
                    pGame.setStarted(true);
                }

            }
        },2);
    }

    public void playStartingAnimationLightGray(Main plugin, Inventory inv, int beginningNumber, int times){
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
            public void run(){
                inv.setItem(beginningNumber,lightGrayGlass);
                if(times > 0){
                    playStartingAnimationLightGray(plugin, inv, beginningNumber+1, times-1);
                }
            }
        },2);
    }

}
