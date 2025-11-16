package com.mukplugins.mukutilsv2.Comandos;

import com.mukplugins.mukutilsv2.MukUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("muk.fly")) {


                if (!(player.getAllowFlight())){
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Você ganhou asas!");

                    return true;
                } else {

                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage(MukUtils.prefix + ChatColor.RED + "Você parou de voar");

                    return true;
                }



            } else {
                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);

                return true;
            }



        }
        return false;
    }
}
