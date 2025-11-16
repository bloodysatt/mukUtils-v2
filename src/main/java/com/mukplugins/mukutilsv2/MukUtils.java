package com.mukplugins.mukutilsv2;

import com.mukplugins.mukutilsv2.Comandos.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MukUtils extends JavaPlugin {

    public static String prefix = ChatColor.GOLD + "[MukUtils] ";
    public static String SenderError = ChatColor.RED + "Apenas Jogadores podem executar este comando." ;
    public static String PermissionError = ChatColor.RED + "Você não tem permissão para executar este comando." ;

    @Override
    public void onEnable() {

        WarpCommand.loadWarpsFromFile(this);

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


        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Plugin Habilitado!");

    }

    @Override
    public void onDisable() {

        WarpCommand.saveWarpsToFile(this);


        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Plugin Desabilitado!");

    }

}
