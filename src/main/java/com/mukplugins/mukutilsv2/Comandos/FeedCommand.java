package com.mukplugins.mukutilsv2.Comandos;

import com.mukplugins.mukutilsv2.MukUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("feed")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);

                return true;

            }

                Player player = (Player) sender;
            if (player.hasPermission("muk.feed")) {
                if (player.getFoodLevel() < 20) {
                    player.setFoodLevel(20);
                    player.setSaturation(20);

                    player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Yummy!");

                    return true;

                } else {
                    player.sendMessage(MukUtils.prefix + ChatColor.RED + "O Jogador não tem fome.");

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
