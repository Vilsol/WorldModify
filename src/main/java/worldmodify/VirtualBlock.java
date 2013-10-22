package worldmodify;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@SuppressWarnings("deprecation")
public class VirtualBlock {

	private Location loc;
	private Material mat;
	private Byte data;
	private Inventory inv;
	
	public VirtualBlock(VirtualBlock vb) {
		loc = vb.getLocation();
		mat = vb.getMaterial();
		data = vb.getData();
		inv = vb.getInventory();
	}
	
	public VirtualBlock(Block block){
		loc = block.getLocation();
		mat = block.getType();
		data = block.getData();
		if(block instanceof InventoryHolder){
			inv = ((InventoryHolder) block).getInventory();
		}
	}
	
	public VirtualBlock(Material material){
		mat = material;
	}
	
	public VirtualBlock(Material material, Location location){
		loc = location;
		mat = material;
	}
	
	public VirtualBlock(Material material, Location location, Byte blockData){
		loc = location;
		mat = material;
		data = blockData;
	}
	
	public VirtualBlock(Material material, Location location, Byte blockData, Inventory blockInventory){
		loc = location;
		mat = material;
		data = blockData;
		inv = blockInventory;
	}

	/**
	 * Returns the virtual block location
	 * @return Virtual location
	 */
	public Location getLocation(){
		return loc;
	}
	
	/**
	 * Returns the virtual block material
	 * @return Blocks material
	 */
	public Material getMaterial(){
		return mat;
	}
	
	/**
	 * Returns the block data
	 * @return Blocks data
	 */
	public Byte getData(){
		return data;
	}
	
	/**
	 * Returns the block inventory
	 * @return Blocks Inventory
	 */
	public Inventory getInventory(){
		return inv;
	}
	
	/**
	 * Sets the block location
	 * @param location New location
	 */
	public void setLocation(Location location){
		loc = location;
	}
	
	/**
	 * Sets the block material
	 * @param material New material
	 */
	public void setMaterial(Material material){
		mat = material;
	}
	
	/**
	 * Sets the block data
	 * @param blockData New data
	 */
	public void setData(Byte blockData){
		data = blockData;
	}
	
	/**
	 * Sets the block inventory
	 * @param inventory New inventory
	 */
	public void setInventory(Inventory inventory){
		inv = inventory;
	}
	
	/**
	 * Places the block in the world
	 */
	public void buildBlock(){
		if(loc != null){
			Block realBlock = loc.getBlock();
			realBlock.setType(mat);
			if(data != null) realBlock.setData(data);
			if(realBlock instanceof InventoryHolder){
				((InventoryHolder) realBlock).getInventory().setContents(inv.getContents());
			}
		}
	}
	
}
