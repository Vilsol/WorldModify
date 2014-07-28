package worldmodify.commands;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

public class CommandReplacenear implements CommandExecutor, ScannerRunner {

	private Material replacing;
	private Material replacement;
	private Queue<VirtualBlock> replaced = new LinkedList<VirtualBlock>();
	private PlayerSession ps;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.replacenear")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			if(args.length >= 3){
				if(Utils.isInteger(args[0]) && Integer.parseInt(args[0]) > 0){
					int distance = Integer.parseInt(args[0]);
					Player plr = (Player) sender;
					PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
					replacing = Material.getMaterial(Integer.parseInt(args[1]));
					replacement = Material.getMaterial(Integer.parseInt(args[2]));
					sender.sendMessage(Utils.prefix + "Detecting replacements...");
					Scanner sc = new Scanner(new VirtualArea(plr.getLocation().add(new Vector(distance, distance, distance)), plr.getLocation().add(new Vector(distance*-1, distance*-1, distance*-1))), this, true, ps);
					sc.scan();
				}
			}
		}else{
			Utils.requirePlayer(sender, "replacenear");
		}
		return true;
	}

	@Override
	public boolean scanBlock(Block block) {
		if(block.getType().equals(replacing)){
			VirtualBlock newBlock = new VirtualBlock(replacement);
			newBlock.setLocation(new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()));
			replaced.add(newBlock);
		}
		
		return false;
	}

	@Override
	public void onFinish(Queue<VirtualBlock> blockList) {
		BuilderSession bs = WMBukkit.makeBuilderSession(replaced, ps);
		if(Utils.isTransparent(new VirtualBlock(replacing))) bs.reverseList();
		bs.build();
		PlayerNotify pn = new PlayerNotify(ps.getPlayer(), bs);
		pn.infoMessage();
		pn.runMessenger();
		return;
	}
	
}