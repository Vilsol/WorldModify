package worldmodify.Commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import worldmodify.BuilderSession;
import worldmodify.PlayerNotify;
import worldmodify.PlayerSession;
import worldmodify.Utils;
import worldmodify.VirtualBlock;
import worldmodify.WMBukkit;

public class CommandReplacenear implements CommandExecutor {

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
					VirtualBlock replace = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[1])));
					VirtualBlock replacement = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[2])));
					sender.sendMessage(Utils.prefix + "Detecting replacements...");
					List<VirtualBlock> blockList = WMBukkit.getVirtualReplaceCuboid(plr.getLocation().add(new Vector(distance, distance, distance)),plr.getLocation().add(new Vector(distance*-1, distance*-1, distance*-1)), replace, replacement);
					BuilderSession bs = WMBukkit.makeBuilderSession(blockList, ps);
					if(Utils.isTransparent(replace)) bs.reverseList();
					bs.build();
					PlayerNotify pn = new PlayerNotify((Player) sender, bs);
					pn.infoMessage();
					pn.runMessenger();
				}
			}
		}else{
			Utils.requirePlayer(sender, "replacenear");
		}
		return true;
	}
	
}