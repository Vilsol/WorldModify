package worldmodify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

import worldmodify.commands.CommandCopy;
import worldmodify.commands.CommandDistr;
import worldmodify.commands.CommandLimit;
import worldmodify.commands.CommandPaste;
import worldmodify.commands.CommandPos1;
import worldmodify.commands.CommandPos2;
import worldmodify.commands.CommandReplace;
import worldmodify.commands.CommandReplacenear;
import worldmodify.commands.CommandSet;
import worldmodify.commands.CommandStack;
import worldmodify.commands.CommandUndo;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.managers.MultiManager;
import worldmodify.listeners.PlayerListener;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.sessions.PluginSession;

public class WorldModify extends JavaPlugin {

	private static boolean debug = false;
	public static WorldModify plugin;
	public static FileConfiguration config;
	public static Integer sessionCount;
	public static List<BuilderSession> builderSessions = new ArrayList<BuilderSession>();
	public static Map<Player, PlayerSession> playerSessions = new HashMap<Player, PlayerSession>();
	public static Map<Plugin, PluginSession> pluginSessions = new HashMap<Plugin, PluginSession>();
	public static Integer limit = 10;
	public static MultiManager<CommandModel> commandManager = new MultiManager<CommandModel>();
	
	public void onEnable(){
		loadConfig();
		plugin = this;
		config = this.getConfig();
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		listConfig();
		loadMetrics();
		initCommands();
	}

	private void initCommands() {
		commandManager.registerObject(new CommandLimit());
		commandManager.registerObject(new CommandPos1());
		commandManager.registerObject(new CommandPos2());
		commandManager.registerObject(new CommandReplace());
		commandManager.registerObject(new CommandSet());
		commandManager.registerObject(new CommandUndo());
		commandManager.registerObject(new CommandReplacenear());
		commandManager.registerObject(new CommandStack());
		commandManager.registerObject(new CommandCopy());
		commandManager.registerObject(new CommandPaste());
		commandManager.registerObject(new CommandDistr());
	}

	private void loadMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    
		    Graph globalLimit = metrics.createGraph("Global block limit");
		    globalLimit.addPlotter(new Metrics.Plotter() {
				@Override
				public int getValue() {
					return limit;
				}
			});
		    
		    metrics.start();
		} catch (IOException e) {
		    getLogger().warning("Metrics failed to load!");
		}
	}

	private void listConfig() {
		getLogger().info("Global limit is set to " + getConfig().getInt("Config.GlobalLimit") + " blocks per tick.");
	}

	private void loadConfig() {
		saveDefaultConfig();
		limit = getConfig().getInt("Config.GlobalLimit");
		debug = getConfig().getBoolean("Config.Debug");
	}
	
	public static void d(Object o){
		if(debug) WorldModify.plugin.getLogger().info(o.toString());
	}
	
}
