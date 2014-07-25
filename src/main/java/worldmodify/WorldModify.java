package worldmodify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import worldmodify.Commands.CommandManager;
import worldmodify.Commands.Copy;
import worldmodify.Commands.Distr;
import worldmodify.Commands.Help;
import worldmodify.Commands.Limit;
import worldmodify.Commands.Paste;
import worldmodify.Commands.Pos1;
import worldmodify.Commands.Pos2;
import worldmodify.Commands.Replace;
import worldmodify.Commands.Replacenear;
import worldmodify.Commands.Set;
import worldmodify.Commands.Stack;
import worldmodify.Commands.Undo;
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
		
		CommandManager.init(this);
		
		initCommands();
	}

	private void initCommands() {
		CommandManager.instance.addCommand(new Help());
		CommandManager.instance.addCommand(new Pos1());
		CommandManager.instance.addCommand(new Pos2());
		CommandManager.instance.addCommand(new Limit());
		CommandManager.instance.addCommand(new Replace());
		CommandManager.instance.addCommand(new Set());
		CommandManager.instance.addCommand(new Undo());
		CommandManager.instance.addCommand(new Replacenear());
		CommandManager.instance.addCommand(new Stack());
		CommandManager.instance.addCommand(new Copy());
		CommandManager.instance.addCommand(new Paste());
		CommandManager.instance.addCommand(new Distr());
	}

	private void listConfig() {
		getLogger().info("Global limit is set to " + getConfig().getInt("Config.GlobalLimit") + " blocks per tick.");
	}

	private void loadConfig() {
		saveDefaultConfig();
		limit = getConfig().getInt("Config.GlobalLimit");
	}
	
}
