package worldmodify.scanner;

import java.util.Queue;

import org.bukkit.block.Block;

import worldmodify.sessions.CommanderSession;
import worldmodify.utils.VirtualBlock;

public interface ScannerRunner {
	
	/**
	 * Gets called when the scanner is scanning a block.
	 * 
	 * @param block Th block to scan
	 * @return If true, the scanner will be cancelled.
	 */
	public boolean scanBlock(Block block);
	
	/**
	 * Gets called when the scanner has finished.
	 * 
	 * @param blockList The list of scanned blocks
	 */
	public void onFinish(Queue<VirtualBlock> blockList, CommanderSession cs, Scanner s);
	
}
