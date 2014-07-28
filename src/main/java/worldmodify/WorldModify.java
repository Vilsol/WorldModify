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
import worldmodify.listeners.PlayerListener;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.sessions.PluginSession;

public class WorldModify extends JavaPlugin {

	public static WorldModify plugin;
	public static FileConfiguration config;
	public static Integer sessionCount;
	public static List<BuilderSession> builderSessions = new ArrayList<BuilderSession>();
	public static Map<Player, PlayerSession> playerSessions = new HashMap<Player, PlayerSession>();
	public static Map<Plugin, PluginSession> pluginSessions = new HashMap<Plugin, PluginSession>();
	public static Integer limit = 10;
	
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
		getCommand(".limit").setExecutor(new CommandLimit());
		getCommand(".pos1").setExecutor(new CommandPos1());
		getCommand(".pos2").setExecutor(new CommandPos2());
		getCommand(".replace").setExecutor(new CommandReplace());
		getCommand(".set").setExecutor(new CommandSet());
		getCommand(".undo").setExecutor(new CommandUndo());
		getCommand(".replacenear").setExecutor(new CommandReplacenear());
		getCommand(".stack").setExecutor(new CommandStack());
		getCommand(".copy").setExecutor(new CommandCopy());
		getCommand(".paste").setExecutor(new CommandPaste());
		getCommand(".distr").setExecutor(new CommandDistr());
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
	}
	
}
