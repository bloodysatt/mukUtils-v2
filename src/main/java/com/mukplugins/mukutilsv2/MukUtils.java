package com.mukplugins.mukutilsv2;

import com.mukplugins.mukutilsv2.Comandos.FeedCommand;
import com.mukplugins.mukutilsv2.Comandos.FlyCommand;
import com.mukplugins.mukutilsv2.Comandos.HealCommand;
import com.mukplugins.mukutilsv2.Comandos.TpaCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MukUtils extends JavaPlugin {
    public static String prefix = ChatColor.GOLD + "[MukUtils] ";

    @Override
    public void onEnable() {
        this.getCommand("feed").setExecutor(new FeedCommand());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("tpa").setExecutor(new TpaCommand());
        this.getCommand("tpaccept").setExecutor(new TpaCommand());
        this.getCommand("tpadeny").setExecutor(new TpaCommand());


        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Plugin Habilitado!");

    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Plugin Desabilitado!");

    }

}
