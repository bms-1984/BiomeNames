package io.bms.biomes.config;

import io.bms.biomes.BiomesMod;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by benjaminsutter on 7/20/17.
 */
public class BiomesConfig {

    public static void initConfig(FileConfiguration config)  {
        config.options().copyDefaults(true);
        String worldName = BiomesMod.getInstance().getServer().getWorlds().get(0).getName();
        for (Biome biome : Biome.values()) {
            if (!config.isSet(String.format("worlds.%s.biomes.%s", worldName, biome.name()))) {
                config.set(String.format("worlds.%s.biomes.%s", worldName, biome.name()), biome.name());
            }
        }
    }

    public static String getCustomBiomeName(Biome biome, String worldName) {
        FileConfiguration config = BiomesMod.getInstance().getConfig();
        if (config.isSet(String.format("worlds.%s.biomes.%s", worldName, biome.name())))
            return config.getString(String.format("worlds.%s.biomes.%s", worldName, biome.name()));
        BiomesMod.logger.warning("Finished getCustomBiomeName with no return value, returning null.");
        return null;
    }
}
