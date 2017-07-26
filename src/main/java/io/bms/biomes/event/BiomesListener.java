package io.bms.biomes.event;

import io.bms.biomes.config.BiomesConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BiomesListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Biome biome = world.getBiome(location.getBlockX(), location.getBlockZ());
        String customBiomeName = BiomesConfig.getCustomBiomeName(biome, world.getName());
        if (customBiomeName != null) {
            player.sendMessage(String.format("[Map] %s", customBiomeName));
        }
    }
}
