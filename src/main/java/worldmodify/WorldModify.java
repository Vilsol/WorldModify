package worldmodify;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldModify extends JavaPlugin {

	public static WorldModify plugin;
	public static FileConfiguration config;
	public static Integer sessionCount;
	public static Map<Integer, PlacerSession> placerSessions = new HashMap<Integer, PlacerSession>();
	public static List<PlayerSession> playerSessions = new ArrayList<PlayerSession>();
	
	public void onEnable(){
		loadConfig();
		plugin = this;
		config = this.getConfig();
		getServer().getPluginManager().registerEvents(new WorldModifyListener(), this);
		listConfig();
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
	}
	
}
