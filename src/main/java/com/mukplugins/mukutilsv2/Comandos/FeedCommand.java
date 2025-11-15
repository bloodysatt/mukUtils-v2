package com.mukplugins.mukutilsv2.Comandos;

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
                sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar este comando");

                return false;
            } else {
                Player player = (Player) sender;
                if (player.getFoodLevel() <20) {
                    player.setFoodLevel(20);
                    player.setSaturation(20);

                    player.sendMessage(ChatColor.GREEN + "Yummy!");


                }else {
                    player.sendMessage(ChatColor.RED + "O Jogador não tem fome.");
                }

            }

        }

        return false;

    }
}
