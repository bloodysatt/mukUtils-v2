package com.mukplugins.mukutilsv2.Comandos;

import com.mukplugins.mukutilsv2.MukUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TellCommand implements CommandExecutor {

    private static HashMap<UUID, UUID> targets = new HashMap<>();
    private static HashMap<UUID, String> messages = new HashMap<UUID, String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);

            return true;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("tell")){

            if (!(player.hasPermission("mukutils.tell"))) {

                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);

                return true;
            }

            if (args.length < 1 ) {

                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Utilização correta: /tell (Jogador) (Mensagem)");

                return true;

            }
            if (args.length > 2 ) {

                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Utilização correta: /tell (Jogador) (Mensagem)");

                return true;

            }

            Player target = Bukkit.getPlayer(args[0]);
            String message = args[1];

            if (target == player) {

                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Você não pode mandar mensagem para si próprio");

                return true;

            }


            if (target != null) {
                //adiciona o alvo ao hashmap
                targets.put(target.getUniqueId(), player.getUniqueId());




                if (message != null) {
                    //manda mensagem para os dois jogadores
                    messages.put(target.getUniqueId(), String.valueOf(player.getUniqueId()));
                    player.sendMessage(ChatColor.GOLD + "[Privado] " + ChatColor.GREEN + player.getName() + ": " + message);
                    target.sendMessage(ChatColor.GOLD + "[Privado] " + player.getName() + ": " + message);

                    return true;
                }
                return true;
            }







        }



        return false;
    }
}
