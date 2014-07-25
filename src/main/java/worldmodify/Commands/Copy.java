package worldmodify.Commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

public class Copy extends CommandBase implements ScannerRunner {

	public Copy() {
		super(".copy", "", "wm.copy");
	}
	
	private PlayerSession ps;

	public void execute(Player player, String[] args) {
		ps = WMBukkit.getPlayerSession(player);
		if (ps.hasSetPos()) {
			boolean excludeAir = Utils.arrContains(args, "-a");
			Location low = Utils.getLowPoint(ps.getPos1(), ps.getPos2());
			ps.setRelativeCopy(Utils.getRelativeCoords(low, player.getLocation()));
			ps.setCopyLocation(Utils.getLowPoint(ps.getPos1(), ps.getPos2()));

			Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
			sc.setExcludeAir(excludeAir);
			sc.scan();
		}
	}
	
	@Override
	public boolean scanBlock(Block block) {
		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList) {
		ps.addToHistory(blockList);
		ps.getPlayer().sendMessage(Utils.prefix + "Blocks copied!");
	}

}
