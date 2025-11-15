package com.mukplugins.mukutilsv2.Comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("heal")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Apenas Jogadores podem usar este comando.");
                return false;
            } else {
                Player player = (Player) sender;
                if (player.getHealth() < 20) {

                    player.setHealth(20);
                    player.sendMessage(ChatColor.GREEN + "Curado com sucesso!");
                } else {
                    player.sendMessage(ChatColor.RED + "O Jogador já tem a vida no máximo");
                }
            }




        }
        return false;
    }
}
