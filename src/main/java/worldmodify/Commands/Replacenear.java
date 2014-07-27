package worldmodify.Commands;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

public class Replacenear extends CommandBase implements ScannerRunner {

	public Replacenear() {
		super(".replacenear", "<distance> <block> <newblock>", "wm.replacenear");
	}

	private Material replacing;
	private Material replacement;
	private Queue<VirtualBlock> replaced = new LinkedList<VirtualBlock>();
	private PlayerSession ps;

	@SuppressWarnings("deprecation")
	public void execute(Player player, String[] args) {
		if (Utils.isInteger(args[0]) && Integer.parseInt(args[0]) > 0) {
			int distance = Integer.parseInt(args[0]);
			ps = WMBukkit.getPlayerSession(player);
			
			if(Utils.getMaterial(args[1]) == null) {
				player.sendMessage(Utils.prefixe + "Invalid block for block to replace!");
				return;
			}
			
			if(Utils.getMaterial(args[2]) == null) {
				player.sendMessage(Utils.prefixe + "Invalid block for replacement block!");
				return;
			}
			
			replacing = Utils.getMaterial(args[1]);
			replacement = Utils.getMaterial(args[2]);
			player.sendMessage(Utils.prefix + "Detecting replacements...");
			Scanner sc = new Scanner(new VirtualArea(player.getLocation().add(new Vector(distance, distance, distance)), player.getLocation().add(new Vector(distance * -1, distance * -1, distance * -1))), this, true, ps);
			sc.scan();
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
