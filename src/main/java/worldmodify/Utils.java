package worldmodify;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Utils {

	private static List<Material> transparentBlocks = Arrays.asList(Material.ACTIVATOR_RAIL, Material.ANVIL, Material.BED, Material.BED_BLOCK, Material.BROWN_MUSHROOM, Material.CACTUS, Material.CAKE_BLOCK, Material.CROPS, Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_OFF, Material.DRAGON_EGG, Material.ITEM_FRAME, Material.LADDER, Material.LAVA, Material.NETHER_WARTS, Material.PAINTING, Material.POWERED_RAIL, Material.RAILS, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.REDSTONE_WIRE, Material.SAND, Material.SAPLING, Material.SEEDS, Material.SIGN, Material.SIGN_POST, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.SUGAR_CANE_BLOCK, Material.TORCH, Material.TRAP_DOOR, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.VINE, Material.WALL_SIGN, Material.WATER, Material.WATER_LILY);
	
	/**
	 * Returns true or false, depending on if the number is an integer.
	 * @param s Input string
	 * @return True if input is integer.
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}

	/**
	 * Takes two locations, combines them and finds the lowest coordinates.
	 * @param pos1 First location
	 * @param pos2 Second location
	 * @return Location with lowest point
	 */
	public static Location getLowPoint(Location pos1, Location pos2) {
		Location lowestPoint = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
		
		if(pos1.getBlockX() < pos2.getBlockX()){
			lowestPoint.setX(pos1.getBlockX()); 
		}else{
			lowestPoint.setX(pos2.getBlockX());
		}
		
		if(pos1.getBlockY() < pos2.getBlockY()){
			lowestPoint.setY(pos1.getBlockY()); 
		}else{
			lowestPoint.setY(pos2.getBlockY());
		}
		
		if(pos1.getBlockZ() < pos2.getBlockZ()){
			lowestPoint.setZ(pos1.getBlockZ()); 
		}else{
			lowestPoint.setZ(pos2.getBlockZ());
		}
		
		return lowestPoint;
	}

	/**
	 * Takes two locations, combines them and finds the highest coordinates.
	 * @param pos1 First location
	 * @param pos2 Second location
	 * @return Location with highest point
	 */
	public static Location getHighestPoint(Location pos1, Location pos2) {
		Location highestPoint = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
		
		if(pos1.getBlockX() > pos2.getBlockX()){
			highestPoint.setX(pos1.getBlockX()); 
		}else{
			highestPoint.setX(pos2.getBlockX());
		}
		
		if(pos1.getBlockY() > pos2.getBlockY()){
			highestPoint.setY(pos1.getBlockY()); 
		}else{
			highestPoint.setY(pos2.getBlockY());
		}
		
		if(pos1.getBlockZ() > pos2.getBlockZ()){
			highestPoint.setZ(pos1.getBlockZ()); 
		}else{
			highestPoint.setZ(pos2.getBlockZ());
		}
		
		return highestPoint;
	}

	
	/**
	 * Returns the total size of the area
	 * @param low First location
	 * @param high Second location
	 * @return Size of the area
	 */
	public static Integer getTotalBlocks(Location low, Location high) {
		int x = high.getBlockX() - low.getBlockX();
		int y = high.getBlockY() - low.getBlockY();
		int z = high.getBlockZ() - low.getBlockZ();
		return x * y * z;
	}

	/**
	 * Returns per-session block limit.
	 * @return Per session block limit.
	 */
	public static int getLocalLimit() {
		return 100;
		//return (int) Math.floor(WorldModify.config.getInt("Config.GlobalLimit") / WorldModify.builderSessions.size());
	}

	/**
	 * Returns whether or not the provided block is transparent
	 * @param vb The virtual block to check
	 * @return Is the block transparent
	 */
	public static boolean isTransparent(VirtualBlock vb) {
		if(transparentBlocks.contains(vb.getMaterial())){
			return true;
		}
		return false;
	}

	/**
	 * Checks if the player has a WorldModify session
	 * @param player Checked player
	 * @return If player has a session
	 */
	public static boolean playerHasSession(Player player) {
		return (WorldModify.playerSessions.containsKey(player));
	}
	
}
