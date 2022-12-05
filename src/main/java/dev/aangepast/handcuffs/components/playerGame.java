package dev.aangepast.handcuffs.components;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class playerGame {

    private List<ItemStack> chosen = new ArrayList<>();

    private List<ItemStack> playerItems = new ArrayList<>();
    private Player player;
    private boolean started;
    private ItemStack lastItem;

    public void removePlayerItems(ItemStack item){
        this.playerItems.remove(item);
    }

    public void addPlayerItems(ItemStack item){
        this.playerItems.add(item);
    }

    public void removeChosenItems(ItemStack item){
        this.chosen.remove(item);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(Player player){
        return player;
    }

    public void addChosenItems(ItemStack item){
        this.chosen.add(item);
    }

    public List<ItemStack> getChosenItems() {
        return chosen;
    }

    public void addAllPlayerItems(List<ItemStack> items){
        this.playerItems.addAll(items);
    }

    public List<ItemStack> getPlayerItems() {
        return playerItems;
    }

    public void setLastItem(ItemStack item){
        this.lastItem = item;
    }

    public void setStarted(boolean started){
        this.started = started;
    }

    public boolean getStarted(){
        return started;
    }

    public ItemStack getLastItem() {
        return lastItem;
    }


}
