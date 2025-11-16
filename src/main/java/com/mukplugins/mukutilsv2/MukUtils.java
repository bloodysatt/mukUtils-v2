package com.mukplugins.mukutilsv2;

import com.mukplugins.mukutilsv2.Comandos.*;
import com.mukplugins.mukutilsv2.MukKits.KitCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class MukUtils extends JavaPlugin {


    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    private File cooldownsFile;
    private FileConfiguration cooldownsConfig;

    public static String prefix = ChatColor.GOLD + "[MukUtils] ";
    public static String SenderError = ChatColor.RED + "Apenas Jogadores podem executar este comando." ;
    public static String PermissionError = ChatColor.RED + "Você não tem permissão para executar este comando." ;

    @Override
    public void onEnable() {

        saveResource("kits.yml", false);


        setupCooldownsFile();
        loadCooldowns();

        WarpCommand.loadWarpsFromFile(this);
        KitCommands.loadKitsfromFile(this);

        this.getCommand("feed").setExecutor(new FeedCommand());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("tpa").setExecutor(new TpaCommand());
        this.getCommand("tpaccept").setExecutor(new TpaCommand());
        this.getCommand("tpadeny").setExecutor(new TpaCommand());
        this.getCommand("setwarp").setExecutor(new WarpCommand());
        this.getCommand("delwarp").setExecutor(new WarpCommand());
        this.getCommand("warps").setExecutor(new WarpCommand());
        this.getCommand("warp").setExecutor(new WarpCommand());


        this.getCommand("kit").setExecutor(new KitCommands(this));


        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Plugin Habilitado!");

    }

    @Override
    public void onDisable() {

        WarpCommand.saveWarpsToFile(this);

        saveCooldowns();

        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Plugin Desabilitado!");

    }




private void setupCooldownsFile() {
        cooldownsFile = new File(getDataFolder(), "cooldowns.yml");
        if (!cooldownsFile.exists()) {
            try {
                cooldownsFile.createNewFile();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Não foi possível criar o ficheiro cooldowns.yml!", e);
            }
        }
        cooldownsConfig = YamlConfiguration.loadConfiguration(cooldownsFile);
    }

    public void loadCooldowns() {
        cooldowns.clear();
        ConfigurationSection section = cooldownsConfig.getConfigurationSection("cooldowns");
        if (section != null) {
            for (String uuidStr : section.getKeys(false)) {
                UUID uuid = UUID.fromString(uuidStr);
                ConfigurationSection playerSection = section.getConfigurationSection(uuidStr);
                if (playerSection != null) {
                    Map<String, Long> playerCooldowns = new HashMap<>();
                    for (String kitName : playerSection.getKeys(false)) {
                        long expiryTime = playerSection.getLong(kitName);
                        playerCooldowns.put(kitName, expiryTime);
                    }
                    cooldowns.put(uuid, playerCooldowns);
                }
            }
        }
    }

    public void saveCooldowns() {
        cooldownsConfig.set("cooldowns", null);

        for (Map.Entry<UUID, Map<String, Long>> playerEntry : cooldowns.entrySet()) {
            String uuidStr = playerEntry.getKey().toString();
            for (Map.Entry<String, Long> kitEntry : playerEntry.getValue().entrySet()) {
                if (kitEntry.getValue() > System.currentTimeMillis()) {
                    cooldownsConfig.set("cooldowns." + uuidStr + "." + kitEntry.getKey(), kitEntry.getValue());
                }
            }
        }
        try {
            cooldownsConfig.save(cooldownsFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Não foi possível salvar o ficheiro cooldowns.yml!", e);
        }
    }

    public Map<UUID, Map<String, Long>> getCooldowns() {
        return cooldowns;
    }

    public void updatePlayerCooldown(UUID playerUUID, String kitName, Long expiryTime) {
        cooldowns.computeIfAbsent(playerUUID, k -> new HashMap<>()).put(kitName, expiryTime);

        saveCooldowns();
    }

}
