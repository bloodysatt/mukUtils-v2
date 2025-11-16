package com.mukplugins.mukutilsv2.Comandos;

import com.mukplugins.mukutilsv2.MukUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class WarpCommand implements CommandExecutor {

    private static HashMap<String, Location> warps = new HashMap<>();
    private static File warpsFile;
    private static FileConfiguration warpsConfig;


    public static void loadWarpsFromFile(JavaPlugin plugin) {
        warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        if (!warpsFile.exists()) {
            try {
                warpsFile.getParentFile().mkdirs();
                warpsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);

        if (warpsConfig.contains("warps")) {
            for (String name : warpsConfig.getConfigurationSection("warps").getKeys(false)) {
                Location loc = (Location) warpsConfig.get("warps." + name);
                warps.put(name, loc);
            }
            plugin.getLogger().info("[MukUtilsV2] Carregados " + warps.size() + " warps.");
        }
    }

    // Este método salva os warps da memória para o arquivo
    public static void saveWarpsToFile(JavaPlugin plugin) {
        warpsConfig.set("warps", null);
        for (String name : warps.keySet()) {
            warpsConfig.set("warps." + name, warps.get(name));
        }
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        JavaPlugin pluginInstance = JavaPlugin.getProvidingPlugin(getClass());



        if (command.getName().equalsIgnoreCase("setwarp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);
                return true;
            }
            Player player = (Player) sender;

            if (!(player.hasPermission("muk.warp.set"))) {
                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Utilização Correta: /setwarp [nome]");
                return true;
            }

            String WarpName = args[0].toLowerCase();
            Location WarpLocation = player.getLocation();

            warps.put(WarpName, WarpLocation);
            saveWarpsToFile(pluginInstance);

            player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Warp " + WarpName + " setada com sucesso!");
            return true;
        }


        if (command.getName().equalsIgnoreCase("delwarp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);
                return true;
            }
            Player player = (Player) sender;

            if (!(player.hasPermission("muk.warp.delete"))) {
                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Utilização Correta: /delwarp [nome]");
                return true;
            }

            String WarpName = args[0].toLowerCase();

            if (!(warps.containsKey(WarpName))) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Essa warp não existe");
                return true;
            }

            warps.remove(WarpName);
            saveWarpsToFile(pluginInstance);

            player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Warp: " + ChatColor.AQUA + WarpName + ChatColor.GREEN + " deletada com sucesso!");
            return true;
        }

        // --- Lógica para /warps ---
        if (command.getName().equalsIgnoreCase("warps")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);
                return true;
            }

            Player player = (Player) sender;

            if (!(player.hasPermission("muk.warp.list"))) {
                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);
                return true;
            }

            if (warps.isEmpty()) {
                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Não existem warps setadas");
                return true;
            }

            Set<String> warpList = warps.keySet();
            String FormatedList = String.join(ChatColor.WHITE + ", " + ChatColor.AQUA, warpList);
            player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Warps disponíveis: " + ChatColor.AQUA + FormatedList);
            return true;
        }


        if (command.getName().equalsIgnoreCase("warp")) {

            if (!(sender instanceof Player)) {

                sender.sendMessage(MukUtils.prefix + MukUtils.SenderError);
                return true;
            }

            Player player = (Player) sender;

            if (!(player.hasPermission("muk.warp"))) {

                player.sendMessage(MukUtils.prefix + MukUtils.PermissionError);
                return true;
            }
            if (args.length != 1) {

                player.sendMessage(MukUtils.prefix + ChatColor.RED + "Utilização correta: /warp [nome]");
                return true;
            }

            String warpName = args[0].toLowerCase();

            if (warps.containsKey(warpName)) {

                Location location = warps.get(warpName);

                player.teleport(location);
                player.sendMessage(MukUtils.prefix + ChatColor.GREEN + "Teleportado para a warp: " + ChatColor.AQUA + warpName);
                return true;
            } else {

                player.sendMessage(MukUtils.prefix + ChatColor.RED + "A warp '" + warpName + "' não existe.");
                return true;
            }
        }

        return false;
    }
}
