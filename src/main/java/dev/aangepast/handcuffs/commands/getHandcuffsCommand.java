package dev.aangepast.handcuffs.commands;

import dev.aangepast.handcuffs.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class getHandcuffsCommand implements CommandExecutor {

    private Main plugin;

    public getHandcuffsCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){plugin.getLogger().info("This command is for in-game users only.");return true;}

        Player player = (Player) sender;

        String displayRaw = plugin.getConfig().getString("handcuffs.item.name");
        String materialRaw = plugin.getConfig().getString("handcuffs.item.material");
        String loreRaw = plugin.getConfig().getString("handcuffs.item.description");
        int customDataModel = plugin.getConfig().getInt("handcuffs.item.custom-model-data");

        if(displayRaw == null || materialRaw == null || loreRaw == null){player.sendMessage(ChatColor.RED + "Config is invalid, contact Administrator.");return true;}

        ItemStack item = new ItemStack(Material.getMaterial(materialRaw));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(customDataModel);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayRaw));
        String[] lores = ChatColor.translateAlternateColorCodes('&', loreRaw).split("%nl%");
        itemMeta.setLore(Arrays.asList(lores));
        item.setItemMeta(itemMeta);

        player.getInventory().addItem(item);
        player.sendMessage(ChatColor.GREEN + "You have received handcuffs.");


        return true;
    }
}