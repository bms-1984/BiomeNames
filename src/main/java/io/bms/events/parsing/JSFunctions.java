package io.bms.events.parsing;

import org.bukkit.Server;
import org.bukkit.World;

/**
 * Created by benjaminsutter on 7/20/17.
 */
public class JSFunctions {
    private static Server server;

    public JSFunctions(Server server) {
        this.server = server;
    }

    /**
     * startRain() starts a rainstorm in the overworld and is one of many possible
     * functions executed a certain amount of times a day in the scripts
     *
     * @author  bms_
     * @version 0.0.1
     * @since   0.0.1
     */
    public static void startRain() {
        World spawnWorld = server.getWorlds().get(0);
        spawnWorld.setStorm(true);
    }
}
