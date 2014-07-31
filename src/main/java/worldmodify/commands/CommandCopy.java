package worldmodify.commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import worldmodify.R;
import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.CommanderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

@CMD(name = ".copy", permission = "wm.copy")
public class CommandCopy extends CommandModel implements PlayerCommand, ScannerRunner<Object> {

	@Override
	public void onFinish(Queue<VirtualBlock> blockList, CommanderSession cs, Object s) {
		cs.setClipboard(blockList);
		((PlayerSession) cs).getPlayer().sendMessage(R.prefix + "Blocks copied!");
	}

	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(p);
		if(ps.hasSetPos()){
			boolean excludeAir = Utils.arrContains(args, "-a");
			Location low = Utils.getLowPoint(ps.getPos1(), ps.getPos2());
			ps.setRelativeCopy(Utils.getRelativeCoords(low, p.getLocation()));
			ps.setCopyLocation(Utils.getLowPoint(ps.getPos1(), ps.getPos2()));
			
			Scanner<Object> sc = new Scanner<Object>(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
			sc.setExcludeAir(excludeAir);
			sc.setAnnounceProgress(true);
			sc.scan();
		}
		return true;
	}
	
	@Override
	public boolean scanBlock(Block block, Object o) {
		return false;
	}

}
