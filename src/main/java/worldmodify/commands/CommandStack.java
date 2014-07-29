package worldmodify.commands;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.CommanderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

@CMD(name = ".stack", permission = "wm.stack")
public class CommandStack extends CommandModel implements PlayerCommand, ScannerRunner {

	private int alterx = 0;
	private int altery = 0;
	private int alterz = 0;
	private int times = 1;
	private Queue<VirtualBlock> stackingBlocks = new LinkedList<VirtualBlock>();
	
	@Override
	public boolean scanBlock(Block block) {
		for(int i = 1; i <= times; i++){
			VirtualBlock virt = new VirtualBlock(block);
			virt.setLocation(new Location(block.getWorld(), block.getX() + (alterx * i), block.getY() + (altery * i), block.getZ() + (alterz * i)));
			stackingBlocks.add(virt);
		}
		
		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList, CommanderSession cs) {
		BuilderSession bs = WMBukkit.makeBuilderSession(stackingBlocks, cs);
		bs.build();
		PlayerNotify pn = new PlayerNotify(((PlayerSession) cs).getPlayer(), bs);
		pn.infoMessage();
		pn.runMessenger();
	}

	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(p);
		if(ps.hasSetPos()){
			if(args.length >= 1 && Utils.isInteger(args[0])) times = Integer.parseInt(args[0]);
			int pi = Utils.dirToInt(p.getLocation().getPitch());
			int ya = Utils.dirToInt(p.getLocation().getYaw());
			
			final Map<String, Integer> size = Utils.getSelectionSize(ps.getPos1(), ps.getPos2());

			if(pi == -1 || pi == 0 || pi == 1){
				if(ya == 5 || ya == 6 || ya == 7) alterx = size.get("xSize"); 
				if(ya == 1 || ya == 2 || ya == 3) alterx = size.get("xSize") * -1; 
				
				if(ya == 0 || ya == 1 || ya == 7 || ya == 8) alterz = size.get("zSize");
				if(ya == 3 || ya == 4 || ya == 5) alterz = size.get("zSize") * -1;
			}

			if(pi == -1 || pi == -2) altery = size.get("ySize");
			if(pi == 1 || pi == 2) altery = size.get("ySize") * -1;

			p.sendMessage(Utils.prefix + "Scanning area...");
			
			boolean excludeAir = Utils.arrContains(args, "-a");

			Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
			sc.setExcludeAir(excludeAir);
			sc.scan();
		}
		return true;
	}
	
}