package worldmodify;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

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
		Location lowestPoint = pos1;
		
		if(pos1.getX() < pos2.getX()){
			lowestPoint.setX(pos1.getX()); 
		}else{
			lowestPoint.setX(pos2.getX());
		}
		
		if(pos1.getY() < pos2.getY()){
			lowestPoint.setX(pos1.getY()); 
		}else{
			lowestPoint.setX(pos2.getY());
		}
		
		if(pos1.getZ() < pos2.getZ()){
			lowestPoint.setX(pos1.getZ()); 
		}else{
			lowestPoint.setX(pos2.getZ());
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
		Location highestPoint = pos1;
		
		if(pos1.getX() < pos2.getX()){
			highestPoint.setX(pos1.getX()); 
		}else{
			highestPoint.setX(pos2.getX());
		}
		
		if(pos1.getY() < pos2.getY()){
			highestPoint.setX(pos1.getY()); 
		}else{
			highestPoint.setX(pos2.getY());
		}
		
		if(pos1.getZ() < pos2.getZ()){
			highestPoint.setX(pos1.getZ()); 
		}else{
			highestPoint.setX(pos2.getZ());
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
		return (int) Math.floor(WorldModify.config.getInt("Config.GlobalLimit") / WorldModify.placerSessions.size());
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
	
}
