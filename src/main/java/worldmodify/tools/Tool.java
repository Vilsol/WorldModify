package worldmodify.tools;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import worldmodify.sessions.PlayerSession;

public interface Tool {

	public boolean onToolLeftClick(PlayerSession p, Block b, BlockFace f);

	public boolean onToolRightClick(PlayerSession p, Block b, BlockFace f);
	
	public String getName();

}
