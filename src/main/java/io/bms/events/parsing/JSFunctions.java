package io.bms.events.parsing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by benjaminsutter on 7/20/17.
 */
public class JSFunctions {
    private static Server server;

    public JSFunctions(Server server) {
        this.server = server;
    }

    public static void startRain() {
        World world = server.getWorlds().get(0);
        world.setStorm(true);
    }

    public static void broadcastMessage(String message) {
        server.broadcastMessage(message);
    }

    public static void spawnEntity(int worldID, int x, int y, int z, Class<Entity> entity) {
        World world = server.getWorlds().get(worldID);
        Location location = new Location(world, (double) x, (double) y, (double) z);
        world.spawn(location, entity);
    }

    // be careful; this does block damage
    public static void explode(int worldID, int x, int y, int z, int radius) {
        World world = server.getWorlds().get(worldID);
        Location location = new Location(world, (double) x, (double) y, (double) z);
        world.createExplosion(location, (float) radius);
    }

    public static void spawnBlock(int worldID, int x, int y, int z, Material block) {
        World world = server.getWorlds().get(worldID);
        Location location = new Location(world, (double) x, (double) y, (double) z);
        location.getBlock().setType(block);
    }
}
