package io.bms.events;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by benjaminsutter on 7/19/17.
 */
public class EventsMod extends JavaPlugin {

    public static String version = "0.0.1";
    public static String name = "EventsMod";
    private static EventsMod instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info(String.format("%s v%s enabled! Parsing scripts.", name, version));
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("%s v%s disabled!", name, version));
    }
}
