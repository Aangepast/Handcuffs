package dev.aangepast.handcuffs;

import dev.aangepast.handcuffs.commands.getHandcuffsCommand;
import dev.aangepast.handcuffs.commands.resetHandcuffsCommand;
import dev.aangepast.handcuffs.components.playerGame;
import dev.aangepast.handcuffs.listeners.*;
import dev.aangepast.handcuffs.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public HashMap<OfflinePlayer, OfflinePlayer> cuffed = new HashMap<>();
    public List<ItemStack> items = new ArrayList<>();
    public List<playerGame> playerGames = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        new Teleportation().runTaskTimer(this, 0 ,20*2);
        getCommand("gethandcuffs").setExecutor(new getHandcuffsCommand(this));
        getCommand("cuffsreset").setExecutor(new resetHandcuffsCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new onDrop(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onFallDamage(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onHotbarSwitch(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onInventoryInteract(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onPickup(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerAttack(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onRightClick(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onSwapItems(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onInventoryClose(this), this);
        saveDefaultConfig();

        File file = new File(getDataFolder() + "/handcuffed.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(String key : config.getKeys(false)){
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(config.getString(key)));
            OfflinePlayer cuffer = Bukkit.getOfflinePlayer(UUID.fromString(key));
            cuffed.put(target, cuffer);
            config.set(key, null);
        }
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // load items for escape mini-game
        ItemStack redGlass = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack blueGlass = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack greenGlass = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack limeGlass = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack magentaGlass = new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack yellowGlass = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack orangeGlass = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack cyanGlass = new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        ItemStack pinkGlass = new ItemBuilder(Material.PINK_STAINED_GLASS_PANE).setName(ChatColor.RESET + " ").toItemStack();
        items.add(redGlass);
        items.add(blueGlass);
        items.add(greenGlass);
        items.add(limeGlass);
        items.add(magentaGlass);
        items.add(yellowGlass);
        items.add(orangeGlass);
        items.add(cyanGlass);
        items.add(pinkGlass);

        getLogger().info("Fully loaded!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        File file = new File(getDataFolder() + "/handcuffed.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(OfflinePlayer player : cuffed.keySet()){
            OfflinePlayer cuffer = cuffed.get(player);
            config.set(cuffer.getUniqueId().toString(),player.getUniqueId().toString());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Teleportation for cuffed player
    public class Teleportation extends BukkitRunnable {

        @Override
        public void run() {
            for(OfflinePlayer player : cuffed.keySet()){
                OfflinePlayer cuffer = cuffed.get(player);
                if(player.isOnline() && cuffer.isOnline()){
                    Player onlinePlayer = (Player) player;
                    Player onlineCuffer = (Player) cuffer;
                    if(onlinePlayer.getOpenInventory().getTitle().equals(ChatColor.RED + "Escape the handcuffs")){
                        onlineCuffer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Handcuffing " + onlinePlayer.getDisplayName() + "..."));
                        return;
                    }
                    onlinePlayer.teleport(onlineCuffer);
                }

            }
        }
    }

}