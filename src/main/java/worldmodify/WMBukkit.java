package worldmodify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WMBukkit {

	/**
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @param pos1 First position
	 * @param pos2 Second position
	 * @return The placer session
	 */
	public static BuilderSession makeBuilderSession(List<VirtualBlock> vb){
		BuilderSession bs = new BuilderSession(vb);
		return bs;
	}
	
	/**
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @param ps Player session
	 * @return The placer session
	 */
	public static BuilderSession makeBuilderSession(List<VirtualBlock> vb, PlayerSession ps){
		BuilderSession bs = new BuilderSession(vb, ps);
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
	public static List<VirtualBlock> getVirtualCuboid(PlayerSession ps, VirtualBlock vb){
		return WMBukkit.getVirtualCuboid(ps.getPos1(), ps.getPos2(), vb);
	}
	
	/**
	 * Creates a new list of blocks that replace existing
	 * @param pos1 First Position
	 * @param pos2 Second Position
	 * @param replace Block to replace
	 * @param replacement Replacement Block
	 * @return List of virtual blocks
	 */
	public static List<VirtualBlock> getVirtualReplaceCuboid(Location pos1, Location pos2, VirtualBlock replace, VirtualBlock replacement){
		List<VirtualBlock> blockList = new ArrayList<VirtualBlock>();
		Location low = Utils.getLowPoint(pos1, pos2);
		Location high = Utils.getHighestPoint(pos1, pos2);
		for(int Y = low.getBlockY(); Y <= high.getBlockY(); Y++){
			for(int X = low.getBlockX(); X <= high.getBlockX(); X++){
				for(int Z = low.getBlockZ(); Z <= high.getBlockZ(); Z++){
					VirtualBlock checkRep = new VirtualBlock(low.getWorld().getBlockAt(X, Y, Z));
					if(checkRep.getMaterial().equals(replace.getMaterial())){
						VirtualBlock newBlock = new VirtualBlock(replacement);
						newBlock.setLocation(new Location(low.getWorld(), X, Y, Z));
						blockList.add(newBlock);
					}
				}
			}
		}
		return blockList;
	}
	
	/**
	 * Creates a new list of blocks that replace existing
	 * @param ps Player Session
	 * @param replace Block to replace
	 * @param replacement Replacement Block
	 * @return List of virtual blocks
	 */
	public static List<VirtualBlock> getVirtualReplaceCuboid(PlayerSession ps, VirtualBlock replace, VirtualBlock replacement){
		return WMBukkit.getVirtualReplaceCuboid(ps.getPos1(), ps.getPos2(), replace, replacement);
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
}
