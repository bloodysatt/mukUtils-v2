package com.mukplugins.mukutilsv2.Comandos;

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
                sender.sendMessage("Apenas jogadores podem utilizar este comando");
            }
            Player player = (Player) sender;
            if (player.hasPermission("muk.fly")) {


                if (!(player.getAllowFlight())){
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage(ChatColor.GREEN + "Você ganhou asas!");
                } else {

                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage(ChatColor.RED + "Você parou de voar");
                }



            } else {
                player.sendMessage(ChatColor.RED + "Você não tem permissão para executar este comando.");
            }



        }
        return false;
    }
}
