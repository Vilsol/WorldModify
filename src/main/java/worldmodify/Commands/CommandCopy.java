package worldmodify.Commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.scanner.Scanner;
import worldmodify.scanner.ScannerRunner;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

public class CommandCopy implements CommandExecutor, ScannerRunner {

	private PlayerSession ps;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.copy")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			Player plr = (Player) sender;
			ps = WMBukkit.getPlayerSession(plr);
			if(ps.hasSetPos()){
				boolean excludeAir = Utils.arrContains(args, "-a");
				Location low = Utils.getLowPoint(ps.getPos1(), ps.getPos2());
				ps.setRelativeCopy(Utils.getRelativeCoords(low, plr.getLocation()));
				ps.setCopyLocation(Utils.getLowPoint(ps.getPos1(), ps.getPos2()));
				
				Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
				sc.setExcludeAir(excludeAir);
				sc.scan();
			}
		}else{
			Utils.requirePlayer(sender, "copy");
		}
		return true;
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
