package worldmodify;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import worldmodify.Metrics.Graph;
import worldmodify.Commands.CommandLimit;
import worldmodify.Commands.CommandPos1;
import worldmodify.Commands.CommandPos2;
import worldmodify.Commands.CommandReplace;
import worldmodify.Commands.CommandReplacenear;
import worldmodify.Commands.CommandSet;
import worldmodify.Commands.CommandUndo;

public class WorldModify extends JavaPlugin {

	public static WorldModify plugin;
	public static FileConfiguration config;
	public static Integer sessionCount;
	public static List<BuilderSession> builderSessions = new ArrayList<BuilderSession>();
	public static Map<Player, PlayerSession> playerSessions = new HashMap<Player, PlayerSession>();
	public static Integer limit = 10;
	
	public void onEnable(){
		loadConfig();
		plugin = this;
		config = this.getConfig();
		getServer().getPluginManager().registerEvents(new WorldModifyListener(), this);
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
		InputStream defConfigStream = getResource("config.yml");
    	YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		getConfig().setDefaults(defConfig);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		limit = getConfig().getInt("Config.GlobalLimit");
	}
	
}
