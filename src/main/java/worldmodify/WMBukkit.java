package worldmodify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import worldmodify.scanners.ReplaceScanner;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.CommanderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.sessions.PluginSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class WMBukkit {
	
	/**
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @param commanderSession Commander session
	 * @return The placer session
	 */
	public static BuilderSession makeBuilderSession(List<VirtualBlock> vb, CommanderSession cs){
		BuilderSession bs = new BuilderSession(vb, cs);
		return bs;
	}
	
	/**
	 * Creates a new list of virtual blocks in a cuboid
	 * @param pos1 First position
	 * @param pos2 Second position
	 * @param vb The virtual block to be filled with
	 * @return List of virtual blocks
	 */
	public static List<VirtualBlock> getVirtualCuboid(Location pos1, Location pos2, VirtualBlock vb){
		List<VirtualBlock> blockList = new ArrayList<VirtualBlock>();
		Location low = Utils.getLowPoint(pos1, pos2);
		Location high = Utils.getHighestPoint(pos1, pos2);
		for(int Y = low.getBlockY(); Y <= high.getBlockY(); Y++){
			for(int X = low.getBlockX(); X <= high.getBlockX(); X++){
				for(int Z = low.getBlockZ(); Z <= high.getBlockZ(); Z++){
					VirtualBlock newBlock = new VirtualBlock(vb);
					newBlock.setLocation(new Location(low.getWorld(), X, Y, Z));
					blockList.add(newBlock);
				}
			}
		}
		
		return blockList;
	}
	
	/**
	 * Creates a new list of virtual blocks in a cuboid
	 * @param ps Player session
	 * @param vb The virtual block to be filled with
	 * @return List of virtual blocks
	 */
	public static List<VirtualBlock> getVirtualCuboid(CommanderSession cs, VirtualBlock vb){
		return WMBukkit.getVirtualCuboid(cs.getPos1(), cs.getPos2(), vb);
	}
	
	/**
	 * Creates a new list of blocks that replace existing
	 * @param ps Player Session
	 * @param replace Block to replace
	 * @param replacement Replacement Block
	 * @return List of virtual blocks
	 */
	public static ReplaceScanner getVirtualReplaceCuboid(CommanderSession cs, VirtualBlock replace, VirtualBlock replacement){
		return new ReplaceScanner(cs.getPos1(), cs.getPos2(), replace, replacement, cs);
	}

	/**
	 * Creates a new session for the player
	 * @param player New player
	 * @return The player session
	 */
	public static PlayerSession createPlayerSession(Player player) {
		if(Utils.playerHasSession(player)) return WorldModify.playerSessions.get(player);
		PlayerSession ps = new PlayerSession(player);
		return ps;
	}
	
	/**
	 * Returns the player session
	 * @param player Player
	 * @return Player Session
	 */
	public static PlayerSession getPlayerSession(Player player){
		return createPlayerSession(player);
	}

	/**
	 * Creates a new session for the plugin
	 * @param player New plguin
	 * @return The plugin session
	 */
	public static PluginSession createPluginSession(Plugin plugin) {
		if(Utils.pluginHasSession(plugin)) return WorldModify.pluginSessions.get(plugin);
		PluginSession ps = new PluginSession(plugin);
		return ps;
	}
	
	/**
	 * Returns the plugin session
	 * @param player Plugin
	 * @return Plugin Session
	 */
	public static PluginSession getPluginSession(Plugin plugin){
		return createPluginSession(plugin);
	}
}
