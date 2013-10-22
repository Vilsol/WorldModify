package worldmodify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WMBukkit {

	/**
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @param pos1 First position
	 * @param pos2 Second position
	 * @return The placer session
	 */
	public static BuilderSession makeBuilderSession(List<VirtualBlock> vb, Location pos1, Location pos2){
		BuilderSession bs = new BuilderSession(vb, pos1, pos2);
		return bs;
	}
	
	/**
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @param ps Player session
	 * @return The placer session
	 */
	public static BuilderSession makeBuilderSession(List<VirtualBlock> vb, PlayerSession ps){
		BuilderSession bs = new BuilderSession(vb, ps.getPos1(), ps.getPos2());
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
					VirtualBlock newBlock = vb;
					newBlock.setLocation(pos1.add(new Vector(X, Y, Z)));
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
		List<VirtualBlock> blockList = new ArrayList<VirtualBlock>();
		Location low = Utils.getLowPoint(ps.getPos1(), ps.getPos2());
		Location high = Utils.getHighestPoint(ps.getPos1(), ps.getPos2());
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
	 * Creates a new session for the player
	 * @param player New player
	 * @return The player session
	 */
	public static PlayerSession createPlayerSession(Player player) {
		if(Utils.playerHasSession(player)) return WorldModify.playerSessions.get(player);
		PlayerSession ps = new PlayerSession(player);
		return ps;
	}
}
