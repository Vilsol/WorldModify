package worldmodify.Commands;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

public class Replace extends CommandBase implements ScannerRunner {

	public Replace() {
		super(".replace", "<block> <newblock>", "wm.replace");
	}

	private Material replacing;
	private Material replacement;
	private Queue<VirtualBlock> replaced = new LinkedList<VirtualBlock>();
	private PlayerSession ps;
	
	@SuppressWarnings("deprecation")
	public void execute(Player player, String[] args) {
		ps = WMBukkit.getPlayerSession(player);
		if (ps.hasSetPos()) {
			
			if(Utils.getMaterial(args[0]) == null) {
				player.sendMessage(Utils.prefixe + "Invalid block for block to replace!");
				return;
			}
			
			if(Utils.getMaterial(args[1]) == null) {
				player.sendMessage(Utils.prefixe + "Invalid block for replacement block!");
				return;
			}
			
			replacing = Utils.getMaterial(args[0]);
			replacement = Utils.getMaterial(args[1]);
			player.sendMessage(Utils.prefix + "Detecting replacements...");

			Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
			sc.scan();
		} else {
			player.sendMessage(Utils.prefixe + "Please set both positions!");
		}
	}

	@Override
	public boolean scanBlock(Block block) {
		if (block.getType().equals(replacing)) {
			VirtualBlock newBlock = new VirtualBlock(replacement);
			newBlock.setLocation(new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()));
			replaced.add(newBlock);
		}

		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList) {
		BuilderSession bs = WMBukkit.makeBuilderSession(replaced, ps);
		if (Utils.isTransparent(new VirtualBlock(replacing)))
			bs.reverseList();
		bs.build();
		PlayerNotify pn = new PlayerNotify(ps.getPlayer(), bs);
		pn.infoMessage();
		pn.runMessenger();
		return;
	}

}
