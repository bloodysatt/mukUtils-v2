package com.mukplugins.mukutilsv2.MukKits;

import com.mukplugins.mukutilsv2.MukUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class KitCommands implements CommandExecutor {

    private static File kitsFile;
    private static FileConfiguration kitsConfig;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("kit")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);
                return true;
            }

            Player player = (Player) sender;

            if (!(player.hasPermission("muk.kit.use"))) {
                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "utilização correta: /kit [nome]");
                return true;
            }

            ConfigurationSection kitsSection = kitsConfig.getConfigurationSection("Kits");
            if (kitsSection == null || kitsSection.getKeys(false).isEmpty()) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Não existem kits disponíveis");
                return true;
            }

            boolean kitFound = false;

            for (String kitName : kitsSection.getKeys(false)) {

                if (kitName.equalsIgnoreCase(args[0])) {
                    kitFound = true;


                    String kitItemsString = kitsSection.getString(kitName);

                    if (kitItemsString != null) {
                        for (String itemString : kitItemsString.split(", ")) {
                            String[] info = itemString.split("-");
                            if (info.length == 2) {
                                try {
                                    int id = Integer.parseInt(info[0]);
                                    int qnt = Integer.parseInt(info[1]);
                                    Material material = Material.getMaterial(id);
                                    if (material != null) {
                                        ItemStack item = new ItemStack(material, qnt);
                                        player.getInventory().addItem(item);
                                    } else {
                                        player.sendMessage(MukUtils.prefix + ChatColor.YELLOW + "Aviso: Material ID " + id + " inválido no kit " + kitName);
                                    }
                                } catch (NumberFormatException e) {
                                    player.sendMessage(MukUtils.prefix + ChatColor.YELLOW + "Aviso: Formato de número inválido no kit " + kitName);
                                }
                            }
                        }
                    }

                    player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Você pegou o kit: " + kitName);
                    return true;
                }
            }



            if (!kitFound) {
                String availableKits = kitsSection.getKeys(false).stream()
                        .collect(Collectors.joining(", "));

                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Este kit não existe. Kits disponíveis: " + ChatColor.YELLOW + availableKits);
            }
        }

        return false;
    }

    public static void loadKitsfromFile(JavaPlugin plugin) {
        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            try {
                kitsFile.getParentFile().mkdirs();
                kitsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        kitsConfig = YamlConfiguration.loadConfiguration(kitsFile);

    }

}
