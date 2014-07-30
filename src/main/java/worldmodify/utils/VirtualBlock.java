package worldmodify.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@SuppressWarnings("deprecation")
public class VirtualBlock {
	
	private Location loc;
	private Material mat;
	private Byte data;
	private Inventory inv;
	private BlockState state;
	
	public VirtualBlock(VirtualBlock vb) {
		loc = vb.getLocation();
		mat = vb.getMaterial();
		data = vb.getData();
		inv = vb.getInventory();
		state = vb.getState();
	}
	
	public VirtualBlock(Block block) {
		loc = block.getLocation();
		mat = block.getType();
		data = block.getData();
		if(block.getState() instanceof InventoryHolder) {
			inv = ((InventoryHolder) block.getState()).getInventory();
		}
		
		if(block.getState() != null) {
			state = block.getState();
		}
	}
	
	public VirtualBlock(Material material) {
		mat = material;
	}
	
	/**
	 * Returns the virtual block location
	 * 
	 * @return Virtual location
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * Returns the virtual block material
	 * 
	 * @return Blocks material
	 */
	public Material getMaterial() {
		return mat;
	}
	
	/**
	 * Returns the block data
	 * 
	 * @return Blocks data
	 */
	public Byte getData() {
		return data;
	}
	
	/**
	 * Returns the block inventory
	 * 
	 * @return Blocks Inventory
	 */
	public Inventory getInventory() {
		return inv;
	}
	
	public BlockState getState() {
		return state;
	}
	
	/**
	 * Sets the block location
	 * 
	 * @param location
	 *            New location
	 */
	public void setLocation(Location location) {
		loc = location;
	}
	
	/**
	 * Sets the block material
	 * 
	 * @param material
	 *            New material
	 */
	public void setMaterial(Material material) {
		mat = material;
	}
	
	/**
	 * Sets the block data
	 * 
	 * @param blockData
	 *            New data
	 */
	public void setData(Byte blockData) {
		data = blockData;
	}
	
	/**
	 * Sets the block inventory
	 * 
	 * @param inventory
	 *            New inventory
	 */
	public void setInventory(Inventory inventory) {
		inv = inventory;
	}
	
	public void setState(BlockState blockState) {
		state = blockState;
	}
	
	/**
	 * Places the block in the world
	 */
	public void buildBlock() {
		if(loc != null) {
			final Block realBlock = loc.getBlock();
			if(realBlock.getState() instanceof InventoryHolder) {
				((InventoryHolder) realBlock.getState()).getInventory().clear();
			}
			realBlock.setType(mat);
			if(data != null) {
				realBlock.setData(data);
			} else {
				realBlock.setData((byte) 0);
			}
			if(inv != null) {
				if(realBlock.getState() instanceof InventoryHolder) {
					((InventoryHolder) realBlock.getState()).getInventory().setContents(inv.getContents());
				}
			}
			if(state != null) {
				Utils.setStates(state, realBlock);
			}
		}
	}
	
	/**
	 * Returns the block at the current location
	 * 
	 * @return The block
	 */
	public Block getBlock() {
		return loc.getBlock();
	}
	
}
