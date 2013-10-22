package worldmodify;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerSession {

	private Player plr;
	private Location pos1;
	private Location pos2;
	
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

}
