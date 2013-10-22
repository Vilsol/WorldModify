package worldmodify;

import java.util.List;

import org.bukkit.Location;

public class WMBukkit {

	/**
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @return The placer session
	 */
	public static PlacerSession makeSession(List<VirtualBlock> vb, Location pos1, Location pos2){
		PlacerSession ps = new PlacerSession(vb, pos1, pos2);
		return ps;
	}
}
