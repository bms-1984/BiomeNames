package io.bms.biomes;

import io.bms.biomes.config.BiomesConfig;
import io.bms.biomes.event.BiomesListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by benjaminsutter on 7/19/17.
 */
public class BiomesMod extends JavaPlugin {

    private FileConfiguration config;
    private static BiomesMod instance;
    public static Logger logger;
    public static BiomesMod getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        config = getConfig();
        BiomesConfig.initConfig(config);
        saveConfig();

        new BiomesListener();
        getServer().getPluginManager().registerEvents(new BiomesListener(), this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("loadbiomes")) {
            if (sender instanceof Player) {
                if (!sender.hasPermission("biomes.loadbiomes")) {
                    return true;
                }
            }
            reloadConfig();
            return true;
        }

        return false;
    }
}
