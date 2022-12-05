package dev.aangepast.handcuffs.commands;

import dev.aangepast.handcuffs.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class resetHandcuffsCommand implements CommandExecutor {
    private Main plugin;

    public resetHandcuffsCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){return true;}

        Player player = (Player) sender;

        if(!(args.length == 1)){player.sendMessage(ChatColor.RED+"Invalid arguments, correct syntax: " + ChatColor.YELLOW + "/cuffsreset <Player>");return true;}
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){player.sendMessage(ChatColor.RED+"Player not found.");return true;}

        player.sendMessage(ChatColor.GREEN + "You have taken off the handcuffs.");
        if(plugin.cuffed.containsKey(target)){
            plugin.cuffed.remove(target);
            target.resetTitle();
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.sendMessage(ChatColor.GREEN + "Your handcuffs have been removed by a God.");
        } else {
            plugin.cuffed.remove(target);
            target.resetTitle();
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.sendMessage(ChatColor.GREEN + "Your effects have been stopped by a God.");
        }

        return true;
    }
}