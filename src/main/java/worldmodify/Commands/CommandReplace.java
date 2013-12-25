package worldmodify.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

public class CommandReplace implements CommandExecutor, ScannerRunner {

	private Material replacing;
	private Material replacement;
	private List<VirtualBlock> replaced = new ArrayList<VirtualBlock>();
	private PlayerSession ps;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.replace")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			if(args.length >= 2){
				ps = WMBukkit.getPlayerSession((Player) sender);
				if(ps.hasSetPos()){
					replacing = Material.getMaterial(Integer.parseInt(args[0]));
					replacement = Material.getMaterial(Integer.parseInt(args[1]));
					sender.sendMessage(Utils.prefix + "Detecting replacements...");
					
					Scanner sc = new Scanner(new VirtualArea(ps.getPos1(), ps.getPos2()), this, true, ps);
					sc.scan();
				}else{
					sender.sendMessage(Utils.prefixe + "Please set both positions!");
				}
			}
		}else{
			Utils.requirePlayer(sender, "replace");
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
	public void onFinish(List<VirtualBlock> blockList) {
		BuilderSession bs = WMBukkit.makeBuilderSession(replaced, ps);
		if(Utils.isTransparent(new VirtualBlock(replacing))) bs.reverseList();
		bs.build();
		PlayerNotify pn = new PlayerNotify(ps.getPlayer(), bs);
		pn.infoMessage();
		pn.runMessenger();
		return;
	}
	
}