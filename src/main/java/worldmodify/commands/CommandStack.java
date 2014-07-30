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
import worldmodify.data.StackData;
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

	@Override
	public boolean scanBlock(Block block) {
		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList, CommanderSession cs, Scanner s) {
		Queue<VirtualBlock> stackingBlocks = new LinkedList<VirtualBlock>();
		
		StackData d = (StackData) s.getReturnData();
		
		for(VirtualBlock v : blockList) {
			Block b = v.getBlock();
			for(int i = 1; i <= d.times; i++) {
				VirtualBlock virt = new VirtualBlock(b);
				virt.setLocation(new Location(b.getWorld(), b.getX() + (d.alterx * i), b.getY() + (d.altery * i), b.getZ() + (d.alterz * i)));
				stackingBlocks.add(virt);
			}
		}
		
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
			StackData d = new StackData();
			
			if(args.length >= 1 && Utils.isInteger(args[0])){
				d.times = Integer.parseInt(args[0]);
			}else{
				d.times = 1;
			}
			int pi = Utils.dirToInt(p.getLocation().getPitch());
			int ya = Utils.dirToInt(p.getLocation().getYaw());
			
			final Map<String, Integer> size = Utils.getSelectionSize(ps.getPos1(), ps.getPos2());

			if(pi == -1 || pi == 0 || pi == 1){
				if(ya == 5 || ya == 6 || ya == 7) d.alterx = size.get("xSize"); 
				if(ya == 1 || ya == 2 || ya == 3) d.alterx = size.get("xSize") * -1; 
				
				if(ya == 0 || ya == 1 || ya == 7 || ya == 8) d.alterz = size.get("zSize");
				if(ya == 3 || ya == 4 || ya == 5) d.alterz = size.get("zSize") * -1;
			}

			if(pi == -1 || pi == -2) d.altery = size.get("ySize");
			if(pi == 1 || pi == 2) d.altery = size.get("ySize") * -1;

			p.sendMessage(Utils.prefix + "Scanning area...");
			
			boolean excludeAir = Utils.arrContains(args, "-a");

			Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
			sc.setExcludeAir(excludeAir);
			sc.setAnnounceProgress(true);
			sc.setReturnData(d);
			sc.scan();
		}
		return true;
	}
	
}