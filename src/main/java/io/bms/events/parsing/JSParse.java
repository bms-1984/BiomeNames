package io.bms.events.parsing;

import io.bms.events.EventsMod;
import org.bukkit.Material;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by benjaminsutter on 7/20/17.
 */
public class JSParse {

    public static ScriptEngine scriptEngine;

    public JSParse(String dataFolderName, ClassLoader cl) throws FileNotFoundException, ScriptException {
        Thread.currentThread().setContextClassLoader(cl);
        this.scriptEngine = new ScriptEngineManager(cl).getEngineByName("nashorn");
        scriptEngine.put("FIRE", Material.FIRE);
        scriptEngine.put("STONE", Material.STONE);
        scriptEngine.put("IRON_ORE", Material.IRON_ORE);
        scriptEngine.eval(new FileReader(String.format("plugins/%s/%s", dataFolderName, EventsMod.script)));
    }
}
