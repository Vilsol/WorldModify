package worldmodify.data;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Material;

import worldmodify.utils.VirtualBlock;

public class ReplaceData {

	public Material replacing;
	public Material replacement;
	public Queue<VirtualBlock> replaced = new LinkedList<VirtualBlock>();
	
}
