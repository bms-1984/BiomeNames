package io.bms.biomes.config;

import io.bms.biomes.BiomesMod;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by benjaminsutter on 7/20/17.
 */
public class BiomesConfig {

    public static void initConfig(FileConfiguration config)  {
        for (World world : BiomesMod.getInstance().getServer().getWorlds()) {
            for (Biome biome : Biome.values()) {
                if (!config.isSet(String.format("worlds.%s.biomes.%s", world.getName(), biome.name()))) {
                    config.set(String.format("worlds.%s.biomes.%s", world.getName(), biome.name()), biome.name());
                }
            }
        }
    }

    public static String getCustomBiomeName(Biome biome, String worldName) {
        FileConfiguration config = BiomesMod.getInstance().getConfig();
        if (config.isSet(String.format("worlds.%s.biomes.%s", worldName, biome.name())))
            return config.getString(String.format("worlds.%s.biomes.%s", worldName, biome.name()));
        return null;
    }
}
