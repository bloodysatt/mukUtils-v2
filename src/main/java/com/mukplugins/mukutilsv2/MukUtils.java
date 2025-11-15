package com.mukplugins.mukutilsv2;

import com.mukplugins.mukutilsv2.Comandos.FeedCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MukUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("feed").setExecutor(new FeedCommand());
        Bukkit.getServer().getConsoleSender().sendMessage("Plugin Habilitado!");

    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("Plugin Desabilitado!");

    }
}
