package dev.aangepast.handcuffs.utils;

import dev.aangepast.handcuffs.Main;
import dev.aangepast.handcuffs.components.playerGame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class gameUtil {

    ItemStack grayGlass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();

    public playerGame getPlayerGame(Player player, Main plugin) {

        for(playerGame pGame : plugin.playerGames){
            if(pGame.getPlayer(player).equals(player)){
                return pGame;
            }
        }
        return null;
    }

    public void showAllItems(Player player, Inventory inv, Main plugin, playerGame pGame){

        pGame.setStarted(false);

        for(int i=0;i<18;i++){

            inv.setItem(i, pGame.getChosenItems().get(i));

        }


        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
            public void run() {

                boolean itemsLeft = false;
                for (int i = 0; i < 18; i++) {

                    if (!inv.getItem(i).getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Your guess")) {
                        inv.setItem(i, grayGlass);
                        itemsLeft = true;
                    }

                }

                if (!itemsLeft){
                     player.closeInventory();
                     plugin.cuffed.remove(player);
                     player.removePotionEffect(PotionEffectType.BLINDNESS);
                     player.resetTitle();
                     player.sendMessage(ChatColor.GREEN + "You have found a way to free yourself.");
                     player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*4, 2));
                }

                player.playSound(player.getLocation(), "entity.villager.yes", 1, 1);
                pGame.setStarted(true);

            }
        }, 10);

    }

}
