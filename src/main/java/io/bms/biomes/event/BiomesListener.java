package io.bms.biomes.event;

import io.bms.biomes.config.BiomesConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class BiomesListener implements Listener {
    private HashMap<Biome, HashSet<UUID>> playerMap = new HashMap<>();

    public BiomesListener() {
        for (Biome biome : Biome.values()) {
            if (!playerMap.containsKey(biome))
                playerMap.put(biome, new HashSet<>());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Biome biome = world.getBiome(location.getBlockX(), location.getBlockZ());
        String customBiomeName = BiomesConfig.getCustomBiomeName(biome, world.getName());
        if (customBiomeName != null) {
            if (!playerMap.get(biome).contains(player.getUniqueId())) {
                playerMap.get(biome).add(player.getUniqueId());
                player.sendMessage(String.format("[Map] %s", customBiomeName));
            }
        }
        for (Map.Entry<Biome, HashSet<UUID>> entry : playerMap.entrySet()) {
            if (entry.getValue().contains(player.getUniqueId())) {
                if (biome != entry.getKey())
                    playerMap.get(entry.getKey()).remove(player.getUniqueId());
            }
        }
    }
}
