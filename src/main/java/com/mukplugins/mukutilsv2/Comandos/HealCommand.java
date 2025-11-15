package com.mukplugins.mukutilsv2.Comandos;

import com.mukplugins.mukutilsv2.MukUtils;
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
            }
                Player player = (Player) sender;
            if (player.hasPermission("muk.heal")) {
                if (player.getHealth() < 20) {

                    player.setHealth(20);
                    player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Curado com sucesso!");
                } else {
                    player.sendMessage(MukUtils.prefix + ChatColor.RED + "O Jogador já tem a vida no máximo");
                }
            } else {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Você não tem permissão para executar este comando.");
            }




        }
        return false;
    }
}
