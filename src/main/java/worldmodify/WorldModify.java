package worldmodify;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import worldmodify.Metrics.Graph;

public class WorldModify extends JavaPlugin {

	public static WorldModify plugin;
	public static FileConfiguration config;
	public static Integer sessionCount;
	public static Map<Integer, BuilderSession> builderSessions = new HashMap<Integer, BuilderSession>();
	public static Map<Player, PlayerSession> playerSessions = new HashMap<Player, PlayerSession>();
	public static Integer limit = 10;
	
	public void onEnable(){
		loadConfig();
		plugin = this;
		config = this.getConfig();
		getServer().getPluginManager().registerEvents(new WorldModifyListener(), this);
		listConfig();
		loadMetrics();
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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length > 0){
			String cmd = args[0];
			if(cmd.equalsIgnoreCase("pos1")){
				if(sender instanceof Player){
					PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
					ps.setPos1(((Player) sender).getLocation());
					sender.sendMessage(Utils.prefix + "Position 1 set!");
				}
			}else if(cmd.equalsIgnoreCase("pos2")){
				if(sender instanceof Player){
					PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
					ps.setPos2(((Player) sender).getLocation());
					sender.sendMessage(Utils.prefix + "Position 2 set!");
				}
			}else if(cmd.equalsIgnoreCase("set")){
				if(sender instanceof Player){
					if(args.length >= 2){
						PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
						if(ps.hasSetPos()){
							VirtualBlock vb = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[1])));
							List<VirtualBlock> blockList = WMBukkit.getVirtualCuboid(playerSessions.get(sender), vb);
							BuilderSession bs = WMBukkit.makeBuilderSession(blockList, playerSessions.get(sender));
							if(Integer.parseInt(args[1]) == 0) bs.reverseList();
							bs.build();
							PlayerNotify pn = new PlayerNotify((Player) sender, bs);
							pn.infoMessage();
							pn.runMessenger();
						}else{
							sender.sendMessage(Utils.prefixe + "Please set both positions!");
						}
					}
				}
			}else if(cmd.equalsIgnoreCase("replace")){
				if(sender instanceof Player){
					if(args.length >= 3){
						PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
						if(ps.hasSetPos()){
							VirtualBlock replace = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[1])));
							VirtualBlock replacement = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[2])));
							List<VirtualBlock> blockList = WMBukkit.getVirtualReplaceCuboid(playerSessions.get(sender), replace, replacement);
							BuilderSession bs = WMBukkit.makeBuilderSession(blockList, playerSessions.get(sender));
							bs.build();
							PlayerNotify pn = new PlayerNotify((Player) sender, bs);
							pn.infoMessage();
							pn.runMessenger();
						}else{
							sender.sendMessage(Utils.prefixe + "Please set both positions!");
						}
					}
				}
			}else if(cmd.equalsIgnoreCase("limit")){
				if(sender instanceof Player){
					if(args.length >= 2){
						if(Utils.isInteger(args[1])){
							limit = Integer.parseInt(args[1]);
							getConfig().set("Config.GlobalLimit", limit);
							saveConfig();
						}else{
							sender.sendMessage(Utils.prefixe + "Limit must be a number!");
						}
					}
				}
			}else if(cmd.equalsIgnoreCase("undo")){
				if(sender instanceof Player){
					BuilderSession bs = WMBukkit.getPlayerSession((Player) sender).undoHistory();
					if(bs != null){
						bs.saveUndo(false);
						bs.reverseList();
						bs.build();
						PlayerNotify pn = new PlayerNotify((Player) sender, bs);
						pn.infoMessage();
						pn.runMessenger();
					}else{
						sender.sendMessage(Utils.prefixe + "Nothing to undo!");
					}
				}
			}
		}else{
			sender.sendMessage("Need at least 1 argument");
		}
		return true;
	}
	
}
