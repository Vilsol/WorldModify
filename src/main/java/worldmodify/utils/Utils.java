package worldmodify.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.NoteBlock;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import worldmodify.R;
import worldmodify.WMBukkit;
import worldmodify.WorldModify;

public class Utils {

	private static List<Material> transparentBlocks = Arrays.asList(Material.ACTIVATOR_RAIL, Material.ANVIL, Material.BED, Material.BED_BLOCK, Material.BROWN_MUSHROOM, Material.CACTUS, Material.CAKE_BLOCK, Material.CROPS, Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_OFF, Material.DRAGON_EGG, Material.ITEM_FRAME, Material.LADDER, Material.LAVA, Material.NETHER_WARTS, Material.PAINTING, Material.POWERED_RAIL, Material.RAILS, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.REDSTONE_WIRE, Material.SAND, Material.SAPLING, Material.SEEDS, Material.SIGN, Material.SIGN_POST, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.SUGAR_CANE_BLOCK, Material.TORCH, Material.TRAP_DOOR, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.VINE, Material.WALL_SIGN, Material.WATER, Material.WATER_LILY, Material.WOOD_DOOR, Material.IRON_DOOR, Material.WOOD_PLATE, Material.STONE_PLATE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.WOOD_BUTTON, Material.STONE_BUTTON, Material.LEVER, Material.GRAVEL, Material.CARROT, Material.POTATO);
	
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
		int x = Math.abs(high.getBlockX() - low.getBlockX() + 1);
		int y = high.getBlockY() - low.getBlockY() + 1;
		int z = Math.abs(high.getBlockZ() - low.getBlockZ() + 1);
		return x * y * z;
	}

	/**
	 * Returns per-session block limit.
	 * @return Per session block limit.
	 */
	public static int getLocalLimit() {
		if(WorldModify.builderSessions == null || WorldModify.builderSessions.size() == 0) return WorldModify.limit;
		if(WorldModify.limit == 0) return 0;
		int limit = (int) Math.ceil(WorldModify.limit / WorldModify.builderSessions.size());
		return (limit > 0) ? limit : 1;
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
	
	/**
	 * Checks if two virtual blocks are equal
	 * @param b1 First Block
	 * @param b2 Second block
	 * @return If two blocks are equal
	 */
	public static boolean isSameVirtualBlock(VirtualBlock b1, VirtualBlock b2){
		if(b1.getData() != b2.getData()) return false;
		if(b1.getInventory() != b2.getInventory()) return false;
		if(b1.getMaterial() != b2.getMaterial()) return false;
		return true;
	}

	/**
	 * Flashes the target block to bedrock
	 * @param location Block location
	 */
	public static void flashBlock(Location location) {
		final Block bl = location.getBlock();
		final VirtualBlock vb = new VirtualBlock(bl);
		final Queue<VirtualBlock> flash = new LinkedList<VirtualBlock>();
		flash.add(vb);
		if(bl.getState() instanceof InventoryHolder){
			InventoryHolder ih = (InventoryHolder) bl.getState();
			ih.getInventory().clear();
		}
		if(!bl.getType().equals(Material.JACK_O_LANTERN)){
			bl.setType(Material.JACK_O_LANTERN);
			Bukkit.getScheduler().scheduleSyncDelayedTask(WorldModify.plugin, new Runnable(){
				@Override
				public void run() {
					WMBukkit.makeBuilderSession(flash, null).build();
				}
			}, 10L);
		}
	}

	/**
	 * Sends a message to sender that he has to be a player to use this command.
	 * @param sender Target sender
	 * @param string Command name
	 * @return
	 */
	public static void requirePlayer(CommandSender sender, String cmd) {
		sender.sendMessage(R.prefixe + "You must be a player to use " + ChatColor.RED + cmd + ChatColor.DARK_RED + "!");
	}

	/**
	 * Sends a message to sender that he has no permission to use a command
	 * @param sender Target sender
	 * @return 
	 */
	public static boolean noPerms(CommandSender sender) {
		sender.sendMessage(R.prefixe + "You don't have permission to do this command!");
		return true;
	}
	
	/**
	 * Converts provided direction to an integer.
	 * @param dir Direction
	 * @return Direction of provided value
	 */
	public static int dirToInt(float dir){
		return Math.round(dir / 45f);
	}
	
	public static BlockFace dirToFace(float dir){
		if(dir < 0) dir += 360;
		if(dir >= 337.5 || dir <= 22.5) return BlockFace.SOUTH;
		if(dir >= 22.5 && dir <= 67.5) return BlockFace.SOUTH_WEST;
		if(dir >= 67.5 && dir <= 112.5) return BlockFace.WEST;
		if(dir >= 112.5 && dir <= 157.5) return BlockFace.NORTH_WEST;
		if(dir >= 157.5 && dir <= 202.5) return BlockFace.NORTH;
		if(dir >= 202.5 && dir <= 247.5) return BlockFace.NORTH_EAST;
		if(dir >= 247.5 && dir <= 292.5) return BlockFace.EAST;
		return BlockFace.SOUTH_EAST;
	}
	
	/**
	 * Gives a map of the size between 2 points
	 * @param pos1 First position
	 * @param pos2 Second position
	 * @return Map with xSize ySize and zSize
	 */
	public static Map<String, Integer> getSelectionSize(Location pos1, Location pos2){
		Location lowPoint = Utils.getLowPoint(pos1, pos2);
		Location highPoint = Utils.getHighestPoint(pos1, pos2);
		
		Map<String, Integer> size = new HashMap<String,Integer>();

		size.put("xSize", highPoint.getBlockX() - lowPoint.getBlockX() + 1);
		size.put("ySize", highPoint.getBlockY() - lowPoint.getBlockY() + 1);
		size.put("zSize", highPoint.getBlockZ() - lowPoint.getBlockZ() + 1);
		
		return size;
	}

	/**
	 * Checks if a string array contains a string
	 * @param haystack String array
	 * @param needle Needle
	 * @return True if contains
	 */
	public static boolean arrContains(String[] haystack, String needle) {
		for(String s : haystack){
			if(s.equalsIgnoreCase(needle)) return true;
		}
		return false;
	}
	
	/**
	 * Returns the relative vector to the coordinates
	 * @param pos1 First location
	 * @param pos2 Second location
	 * @return Vector with relative coordinates
	 */
	public static Vector getRelativeCoords(Location pos1, Location pos2){
		return new Vector(pos1.getBlockX() - pos2.getBlockX(), pos1.getBlockY() - pos2.getBlockY(), pos1.getBlockZ() - pos2.getBlockZ());
	}
	
	/**
	 * Change all the block positions inside the list
	 * @param blockList List of blocks
	 * @param difference Vector with difference
	 * @return Altered list of blocks
	 */
	public static Queue<VirtualBlock> alterBlockPosition(Queue<VirtualBlock> blockList, Vector difference){
		Queue<VirtualBlock> newList = new LinkedList<VirtualBlock>();
		for(VirtualBlock vb : blockList){
			VirtualBlock vbs = new VirtualBlock(vb);
			vbs.setLocation(new Location(vb.getLocation().getWorld(), vb.getLocation().getBlockX(), vb.getLocation().getBlockY(), vb.getLocation().getBlockZ()).add(difference));
			newList.add(vbs);
		}
		return newList;
	}
	
	/**
	 * This sorts a map containing a material and integer by the integer
	 * @param map Map with materials and integers
	 * @return Sorted map
	 */
	public static Map<Material, Integer> sortByValue(Map<Material, Integer> map) {
        List<Map.Entry<Material, Integer>> list = new LinkedList<Map.Entry<Material, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Material, Integer>>() {
            public int compare(Map.Entry<Material, Integer> m1, Map.Entry<Material, Integer> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });

        Map<Material, Integer> result = new LinkedHashMap<Material, Integer>();
        for (Map.Entry<Material, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	
	/**
	 * Checks if the plugin has a WorldModify session
	 * @param player Checked plugin
	 * @return If plguin has a session
	 */
	public static boolean pluginHasSession(Plugin plugin) {
		return (WorldModify.pluginSessions.containsKey(plugin));
	}
	
	public static <T> Queue<T> reverseQueue(Queue<T> src){
		Queue<T> dest = new LinkedList<T>();
		Stack<T> s = new Stack<T>();
		while(src.peek() != null) s.add(src.remove());
		while(s.size() > 0) dest.add(s.pop());
	    return dest;
	}
	
	public static void setStates(BlockState s, Block b){
		if(s instanceof BrewingStand && b.getState() instanceof BrewingStand){
			BrewingStand d = (BrewingStand) b.getState();
			d.setBrewingTime(((BrewingStand) s).getBrewingTime());
			d.update(true);
		}else if(s instanceof CommandBlock && b.getState() instanceof CommandBlock){
			CommandBlock d = (CommandBlock) b.getState();
			d.setCommand(((CommandBlock) s).getCommand());
			d.setName(((CommandBlock) s).getName());
			d.update(true);
		}else if(s instanceof CreatureSpawner && b.getState() instanceof CreatureSpawner){
			CreatureSpawner d = (CreatureSpawner) b.getState();
			d.setSpawnedType(((CreatureSpawner) s).getSpawnedType());
			d.setDelay(((CreatureSpawner) s).getDelay());
			d.update(true);
		}else if(s instanceof Furnace && b.getState() instanceof Furnace){
			Furnace d = (Furnace) b.getState();
			d.setBurnTime(((Furnace) s).getBurnTime());
			d.setCookTime(((Furnace) s).getCookTime());
			d.update(true);
		}else if(s instanceof Jukebox && b.getState() instanceof Jukebox){
			Jukebox d = (Jukebox) b.getState();
			d.setPlaying(((Jukebox) s).getPlaying());
			d.update(true);
		}else if(s instanceof NoteBlock && b.getState() instanceof NoteBlock){
			NoteBlock d = (NoteBlock) b.getState();
			d.setNote(((NoteBlock) s).getNote());
			d.update(true);
		}else if(s instanceof Sign && b.getState() instanceof Sign){
			Sign d = (Sign) b.getState();
			int i = 0;
			for(String x : ((Sign) s).getLines()) d.setLine(i++, x);
			d.update(true);
		}else if(s instanceof Skull && b.getState() instanceof Skull){
			Skull d = (Skull) b.getState();
			d.setRotation(((Skull) s).getRotation());
			d.setSkullType(((Skull) s).getSkullType());
			if(((Skull) s).hasOwner()) d.setOwner(((Skull) s).getOwner());
			d.update(true);
		}
	}
	
}
