package dev.aangepast.handcuffs.listeners;

import dev.aangepast.handcuffs.Main;
import dev.aangepast.handcuffs.inventories.escapeInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class onRightClick implements Listener {

    private Main plugin;

    public onRightClick(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent e){

        if(!(e.getHand().equals(EquipmentSlot.HAND))){return;}

        Player player = e.getPlayer();

        if(!player.hasPermission("handcuffs.police")){player.sendMessage(ChatColor.RED + "You don't know how to use these handcuffs.");return;}

        String displayRaw = plugin.getConfig().getString("handcuffs.item.name");
        String materialRaw = plugin.getConfig().getString("handcuffs.item.material");
        String loreRaw = plugin.getConfig().getString("handcuffs.item.description");
        int customDataModel = plugin.getConfig().getInt("handcuffs.item.custom-model-data");

        if(displayRaw == null || materialRaw == null || loreRaw == null){player.sendMessage(ChatColor.RED + "Config is invalid, contact Administrator.");return;}

        ItemStack item = new ItemStack(Material.getMaterial(materialRaw));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(customDataModel);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayRaw));
        String[] lores = ChatColor.translateAlternateColorCodes('&', loreRaw).split("%nl%");
        itemMeta.setLore(Arrays.asList(lores));
        item.setItemMeta(itemMeta);

        if(player.getInventory().getItemInMainHand().equals(item)){

            if(e.getRightClicked().getType().equals(EntityType.PLAYER)){

                Player target = (Player) e.getRightClicked();

                if(!(plugin.cuffed.containsKey(target))){

                    if(!(plugin.cuffed.containsValue(player))){
                        plugin.cuffed.put(target, player);
                        target.sendMessage(ChatColor.RED + "You have been cuffed by " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED + "!");
                        target.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "You have 20 seconds to free yourself...");
                        player.sendMessage(ChatColor.GREEN + "You have cuffed " + ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + "' hands.");
                        player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Cuffing other players can take up to 20 seconds!");
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999999, 255));
                        target.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c&lHANDCUFFED"), ChatColor.translateAlternateColorCodes('&', "&7Your hands have been cuffed!"), 0, 999999999, 0);
                        new escapeInventory(target, plugin);


                        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
                            public void run(){
                                if(target.getOpenInventory().getTitle().equals(ChatColor.RED + "Escape the handcuffs")){
                                    target.closeInventory();
                                    target.sendMessage(ChatColor.RED + "You ran out of time. " + ChatColor.GRAY + "(20 seconds)");
                                }
                            }
                        },20*20);


                    } else {
                        player.sendMessage(ChatColor.RED + "You can only cuff up to 1 player at the time.");
                    }


                } else {

                    if(plugin.cuffed.containsKey(target)){

                        target.sendMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED + " has taken off the handcuffs.");
                        player.sendMessage(ChatColor.GREEN + "You took " + ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + "'s hands out of the handcuffs.");
                        target.removePotionEffect(PotionEffectType.BLINDNESS);
                        plugin.cuffed.remove(target);
                        target.resetTitle();
                        target.closeInventory();
                    }

                }

            }

        }

    }

}