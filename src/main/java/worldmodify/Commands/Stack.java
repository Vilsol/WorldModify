package worldmodify.Commands;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.bukkit.Location;
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

public class Stack extends CommandBase implements ScannerRunner {

	public Stack() {
		super(".stack", "", "wm.stack");
	}

	private PlayerSession ps;
	private int alterx = 0;
	private int altery = 0;
	private int alterz = 0;
	private int times = 1;
	private Queue<VirtualBlock> stackingBlocks = new LinkedList<VirtualBlock>();
	
	public void execute(Player player, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(player);
		if(ps.hasSetPos()){
			if(args.length >= 1 && Utils.isInteger(args[0])) times = Integer.parseInt(args[0]);
			int pi = Utils.dirToInt(player.getLocation().getPitch());
			int ya = Utils.dirToInt(player.getLocation().getYaw());
			
			final Map<String, Integer> size = Utils.getSelectionSize(ps.getPos1(), ps.getPos2());

			if(pi == -1 || pi == 0 || pi == 1){
				if(ya == 5 || ya == 6 || ya == 7) alterx = size.get("xSize"); 
				if(ya == 1 || ya == 2 || ya == 3) alterx = size.get("xSize") * -1; 
				
				if(ya == 0 || ya == 1 || ya == 7 || ya == 8) alterz = size.get("zSize");
				if(ya == 3 || ya == 4 || ya == 5) alterz = size.get("zSize") * -1;
			}

			if(pi == -1 || pi == -2) altery = size.get("ySize");
			if(pi == 1 || pi == 2) altery = size.get("ySize") * -1;

			player.sendMessage(Utils.prefix + "Scanning area...");
			
			boolean excludeAir = Utils.arrContains(args, "-a");

			Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
			sc.setExcludeAir(excludeAir);
			sc.scan();
		}
	}

	@Override
	public boolean scanBlock(Block block) {
		for (int i = 1; i <= times; i++) {
			VirtualBlock virt = new VirtualBlock(block);
			virt.setLocation(new Location(block.getWorld(), block.getX() + (alterx * i), block.getY() + (altery * i), block.getZ() + (alterz * i)));
			stackingBlocks.add(virt);
		}

		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList) {
		BuilderSession bs = WMBukkit.makeBuilderSession(stackingBlocks, ps);
		bs.build();
		PlayerNotify pn = new PlayerNotify(ps.getPlayer(), bs);
		pn.infoMessage();
		pn.runMessenger();
	}

}
