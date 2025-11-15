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

public class TpaCommand implements CommandExecutor {

    //onde ficam guardados os pedidos de tpa
    private static HashMap<UUID, UUID> requests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas Jogadores podem executar este comando");
            return true;
        }
        Player player = (Player) sender;

        if ((command.getName().equalsIgnoreCase("tpa"))) {
            if (!(player.hasPermission("muk.tpa.sent"))) {
                player.sendMessage(ChatColor.RED + "Você não tem permissão para executar este comando");

                return true;
            }

            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "utilização correta: /tpa [Jogador]");

                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == player) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Você não pode enviar tpa para si próprio");
                return true;
            }

            if (target != null) {
                //adiciona o alvo ao hashmap e manda mensagem para os dois jogadores
                requests.put(target.getUniqueId(), player.getUniqueId());
                player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Você enviou um pedido de teleporte para " + target.getName());
                target.sendMessage(MukUtils.prefix + ChatColor.GREEN + player.getName() + " enviou um pedido de teleporte para você, use /tpaccept para aceitar!");

                return true;
            }
            player.sendMessage(MukUtils.prefix + ChatColor.RED + "Alvo não encontrado.");

            return true;


        }

        if (command.getName().equalsIgnoreCase("tpaccept")){
            //Manda mensagem para os dois jogadores e remove o pedido de tpa do hashmap que fiz la em cima :3 (teleporta o jogador inicial para o alvo)
            if (requests.containsKey(player.getUniqueId())) {
                player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Pedido de Tpa aceito!");
                Bukkit.getPlayer(requests.get(player.getUniqueId())).sendMessage(MukUtils.prefix + ChatColor.GREEN + player.getName() + " Aceitou o seu pedido de teleporte");
                Bukkit.getPlayer(requests.get(player.getUniqueId())).teleport(player);
                requests.remove(player.getUniqueId());

                return true;
            }
            player.sendMessage(MukUtils.prefix + ChatColor.RED + "Nenhum pedido de tpa pendente");

        }
        if (command.getName().equalsIgnoreCase("tpadeny")) {
            //mesma coisa que o outro mas sem fazer o teleporte
            if (requests.containsKey(player.getUniqueId())) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Pedido de Tpa negado!");
                Bukkit.getPlayer(requests.get(player.getUniqueId())).sendMessage(MukUtils.prefix + ChatColor.RED + player.getName() + " Recusou o seu pedido de teleporte");
                requests.remove(player.getUniqueId());

                return true;
            }
            player.sendMessage(MukUtils.prefix + ChatColor.RED + "Nenhum pedido de tpa pendente");

            return true;

        }

        return true;
    }
}
