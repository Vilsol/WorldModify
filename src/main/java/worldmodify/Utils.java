package worldmodify;

import java.util.List;

public class Utils {

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
	 * Creates a new placer session.
	 * @param vb List of all virtual blocks to be placed;
	 * @return The placer session
	 */
	public static PlacerSession makeSession(List<VirtualBlock> vb){
		
		return null;
	}
	
}
