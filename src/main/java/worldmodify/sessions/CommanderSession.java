package worldmodify.sessions;

import java.util.Queue;
import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import worldmodify.WMBukkit;
import worldmodify.utils.VirtualBlock;

public abstract class CommanderSession {

	private Location pos1;
	private Location pos2;
	private Stack<Queue<VirtualBlock>> history = new Stack<Queue<VirtualBlock>>();
	private Queue<VirtualBlock> clipboard;
	private Vector relativeCopy;
	private Location copyLocation;
	
	/**
	 * Returns the first selection point
	 * @return First position
	 */
	public Location getPos1(){
		return pos1;
	}
	
	/**
	 * Returns the secons selection point
	 * @return Second position
	 */
	public Location getPos2(){
		return pos2;
	}
	
	/**
	 * Sets the first selection point
	 * @param location Point location
	 */
	public void setPos1(Location location){
		pos1 = location;
	}
	
	/**
	 * Sets the second selection point
	 * @param location Point location
	 */
	public void setPos2(Location location){
		pos2 = location;
	}

	/**
	 * Checks whether the player has both positions set
	 * @return True if set
	 */
	public boolean hasSetPos(){
		return (pos1 != null && pos2 != null);
	}
	
	/**
	 * Returns the player history
	 * @return Returns history
	 */
	public Stack<Queue<VirtualBlock>> getHistory(){
		return history;
	}
	
	/**
	 * Adds a block list to the player history 
	 * @param blockList VirtualBlock list
	 */
	public void addToHistory(Queue<VirtualBlock> blockList){
		history.add(blockList);
	}
	
	/**
	 * Clears the players history
	 */
	public void clearHistory(){
		history.clear();
	}
	
	/**
	 * Returns the last undo from the players history
	 * @return Last undo or null
	 */
	public BuilderSession undoHistory(){
		if(history.size() > 0){
			BuilderSession bs = WMBukkit.makeBuilderSession(history.pop(), this);
			return bs;
		}
		return null;
	}
	
	public Queue<VirtualBlock> popHistory(){
		if(history.size() == 0) return null;
		return history.pop();
	}
	
	/**
	 * Returns the relative position from lowest point of the clipboard
	 * @return Vector with relative coordinates
	 */
	public Vector getRelativeCopy(){
		return relativeCopy;
	}
	
	/**
	 * Returns the players clipboard
	 * @return List of blocks in clipboard
	 */
	public Queue<VirtualBlock> getClipboard(){
		return clipboard;
	}
	
	/**
	 * Sets the clipboard
	 * @param blockList List containing clipboard
	 */
	public void setClipboard(Queue<VirtualBlock> blockList){
		clipboard = blockList;
	}
	
	/**
	 * Sets the relative position of clipboard
	 * @param rel Relative position
	 */
	public void setRelativeCopy(Vector rel){
		relativeCopy = rel;
	}
	
	/**
	 * Sets the location of last copy
	 * @param loc Copy location
	 */
	public void setCopyLocation(Location loc){
		copyLocation = loc;
	}
	
	/**
	 * Returns the location of last copy
	 * @return Last copy location
	 */
	public Location getCopyLocation(){
		return copyLocation;
	}
	
}
