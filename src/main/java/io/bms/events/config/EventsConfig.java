package io.bms.events.config;

import io.bms.events.EventsMod;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by benjaminsutter on 7/20/17.
 */
public class EventsConfig {

    public static void initConfig(FileConfiguration config)  {
        config.options().copyDefaults(true);
        EventsMod.execDays = config.getInt("general.executeEventNTimesADay");
        EventsMod.script = config.getString("general.script");
    }
}
