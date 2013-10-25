package worldmodify;

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
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length > 0){
			String cmd = args[0];
			if(cmd.equalsIgnoreCase("pos1")){
				if(sender instanceof Player){
					WMBukkit.createPlayerSession((Player) sender);
					playerSessions.get(sender).setPos1(((Player) sender).getLocation());
					sender.sendMessage(Utils.prefix + "Position 1 set!");
				}
			}else if(cmd.equalsIgnoreCase("pos2")){
				if(sender instanceof Player){
					WMBukkit.createPlayerSession((Player) sender);
					playerSessions.get(sender).setPos2(((Player) sender).getLocation());
					sender.sendMessage(Utils.prefix + "Position 2 set!");
				}
			}else if(cmd.equalsIgnoreCase("set")){
				if(sender instanceof Player){
					if(args.length >= 2){
						if(WMBukkit.getPlayerSession((Player) sender).hasSetPos()){
							VirtualBlock vb = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[1])));
							List<VirtualBlock> blockList = WMBukkit.getVirtualCuboid(playerSessions.get(sender), vb);
							BuilderSession bs = WMBukkit.makeBuilderSession(blockList, playerSessions.get(sender));
							bs.build();
							PlayerNotify pn = new PlayerNotify((Player) sender, bs);
							pn.infoMessage();
							pn.runMessenger();
						}
					}
				}
			}else if(cmd.equalsIgnoreCase("replace")){
				if(sender instanceof Player){
					if(args.length >= 3){
						if(WMBukkit.getPlayerSession((Player) sender).hasSetPos()){
							VirtualBlock replace = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[1])));
							VirtualBlock replacement = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[2])));
							List<VirtualBlock> blockList = WMBukkit.getVirtualReplaceCuboid(playerSessions.get(sender), replace, replacement);
							BuilderSession bs = WMBukkit.makeBuilderSession(blockList, playerSessions.get(sender));
							bs.build();
							PlayerNotify pn = new PlayerNotify((Player) sender, bs);
							pn.infoMessage();
							pn.runMessenger();
						}
					}
				}
			}else if(cmd.equalsIgnoreCase("limit")){
				if(sender instanceof Player){
					if(args.length >= 2){
						limit = Integer.parseInt(args[1]);
					}
				}
			}
		}else{
			sender.sendMessage("Need at least 1 argument");
		}
		return true;
	}
	
}
