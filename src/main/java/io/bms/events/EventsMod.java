package io.bms.events;

import io.bms.events.config.EventsConfig;
import io.bms.events.parsing.JSFunctions;
import io.bms.events.parsing.JSParse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import static io.bms.events.parsing.JSParse.scriptEngine;

/**
 * Created by benjaminsutter on 7/19/17.
 */
public class EventsMod extends JavaPlugin {

    public static int execTicks;
    public static String script;
    private FileConfiguration config;
    private static EventsMod instance;
    public static Logger logger;
    public static BukkitScheduler scheduler;
    public static String returnedFunctionName;

    public static EventsMod getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        logger.info("Parsing scripts and configs");

        config = this.getConfig();
        saveDefaultConfig(); // this does NOT overwrite an existing file, thank goodness
        EventsConfig.initConfig(config);

        try {
            new JSParse(getDataFolder().getName(), this.getClass().getClassLoader());
        } catch (FileNotFoundException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            EventsMod.logger.warning("The parser was not initialized because of an exception. Stacktrace:");
            e.printStackTrace(pw);
            EventsMod.logger.warning(sw.toString());
        } catch (ScriptException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            EventsMod.logger.warning("The parser was not initialized because of an exception. Stacktrace:");
            e.printStackTrace(pw);
            EventsMod.logger.warning(sw.toString());
        }

        if (scriptEngine == null) {
            logger.info("Nashorn Script Engine returns null, are you using Java 8 or later?");
        }

        new JSFunctions(getServer());

        runEvent();
    }

    public void runEvent() {
        scheduler = getServer().getScheduler();
        // this will execute a random event by calling callEvents execDays times a day
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Invocable invocable = (Invocable) scriptEngine;
                EventsMod.logger.info("Calling a random event.");
                try {
                    returnedFunctionName = (String) invocable.invokeFunction("callEvents");
                } catch (ScriptException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    EventsMod.logger.warning("The event was not called because of an exception. Stacktrace:");
                    e.printStackTrace(pw);
                    EventsMod.logger.warning(sw.toString());
                } catch (NoSuchMethodException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    EventsMod.logger.warning("The event was not called because of an exception. Stacktrace:");
                    e.printStackTrace(pw);
                    EventsMod.logger.warning(sw.toString());
                }
            }
        }, 0L, execTicks);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("runevent")) {
            if (sender instanceof Player) {
                if (!((Player) sender).hasPermission("events.runevent")) {
                    return true;
                }
            }
            if (args.length == 1) {
                sender.sendMessage(String.format("Running %s.", args[0]));
                Invocable invocable = (Invocable) scriptEngine;
                try {
                    returnedFunctionName = (String) invocable.invokeFunction(args[0]);
                } catch (ScriptException e) {
                    sender.sendMessage("An internal error prevented execution. Check the console for the stacktrace.");
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    EventsMod.logger.warning("The event was not called because of an exception. Stacktrace:");
                    e.printStackTrace(pw);
                    EventsMod.logger.warning(sw.toString());
                } catch (NoSuchMethodException e) {
                    sender.sendMessage("That event name doesn't exist.");
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    EventsMod.logger.warning("The event was not called because of an exception. Stacktrace:");
                    e.printStackTrace(pw);
                    EventsMod.logger.warning(sw.toString());
                }
            }
            else if (args.length == 0) {
                sender.sendMessage(String.format("Running %s.", returnedFunctionName));
                runEvent();
            }
            else {
                sender.sendMessage("The runevent command either takes one or zero arguments.");
            }

            return true;
        }

        if (cmd.getName().equalsIgnoreCase("parse")) {
            if (sender instanceof Player) {
                if (!((Player) sender).hasPermission("events.parse")) {
                    return true;
                }
            }
            if (args.length == 1) {
                try {
                    scriptEngine.eval(args[0]);
                } catch (ScriptException e) {
                    sender.sendMessage("That line is invalid Javascript.");
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    EventsMod.logger.warning("The line wasn't run because of an exception. Stacktrace:");
                    e.printStackTrace(pw);
                    EventsMod.logger.warning(sw.toString());
                }
                sender.sendMessage("Line running!");
            }
            else {
                sender.sendMessage("The parse commands takes one argument.");
            }

            return true;
        }
        return false;
    }
}
