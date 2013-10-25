package worldmodify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerSession {

	private Player plr;
	private Location pos1;
	private Location pos2;
	private List<List<VirtualBlock>> history = new ArrayList<List<VirtualBlock>>();
	
	public PlayerSession(Player player) {
		plr = player;
		WorldModify.playerSessions.put(plr, this);
	}

	/**
	 * Returns the session owner
	 * @return Session owner
	 */
	public Player getPlayer() {
		return plr;
	}
	
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
	public List<List<VirtualBlock>> getHistory(){
		return history;
	}
	
	/**
	 * Adds a block list to the player history 
	 * @param blockList VirtualBlock list
	 */
	public void addToHistory(List<VirtualBlock> blockList){
		history.add(blockList);
	}
	
	/**
	 * Clears the players history
	 */
	public void clearHistory(){
		history.clear();
	}
	
	public void undoHistory(){
		
	}
	
}
