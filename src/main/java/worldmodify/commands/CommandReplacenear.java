package worldmodify.commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.data.ReplaceData;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.CommanderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

@CMD(name = ".replacenear", permission = "wm.replacenear")
public class CommandReplacenear extends CommandModel implements PlayerCommand, ScannerRunner {

	@Override
	public boolean scanBlock(Block block, Object o) {
		ReplaceData r = (ReplaceData) o;
		if(block.getType().equals(r.replacing)){
			VirtualBlock newBlock = new VirtualBlock(r.replacement);
			newBlock.setLocation(new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()));
			r.replaced.add(newBlock);
		}
		
		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList, CommanderSession cs, Scanner s) {
		ReplaceData r = (ReplaceData) s.getReturnData();
		BuilderSession bs = WMBukkit.makeBuilderSession(r.replaced, cs);
		if(Utils.isTransparent(new VirtualBlock(r.replacing))) bs.reverseList();
		bs.build();
		PlayerNotify pn = new PlayerNotify(((PlayerSession) cs).getPlayer(), bs);
		pn.infoMessage();
		pn.runMessenger();
		return;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		if(args.length >= 3){
			if(Utils.isInteger(args[0]) && Integer.parseInt(args[0]) > 0){
				int distance = Integer.parseInt(args[0]);
				PlayerSession ps = WMBukkit.getPlayerSession(p);
				ReplaceData r = new ReplaceData();
				r.replacing = Material.getMaterial(Integer.parseInt(args[1]));
				r.replacement = Material.getMaterial(Integer.parseInt(args[2]));
				p.sendMessage(Utils.prefix + "Detecting replacements...");
				Scanner sc = new Scanner(new VirtualArea(p.getLocation().add(new Vector(distance, distance, distance)), p.getLocation().add(new Vector(distance*-1, distance*-1, distance*-1))), this, true, ps);
				sc.setReturnData(r);
				sc.scan();
			}
		}
		return false;
	}
	
}