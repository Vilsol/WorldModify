package worldmodify.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import worldmodify.WMBukkit;
import worldmodify.scanners.ReplaceScanner;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

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
					new ReplaceScanner(plr.getLocation().add(new Vector(distance, distance, distance)), plr.getLocation().add(new Vector(distance*-1, distance*-1, distance*-1)), replace, replacement, ps); 
				}
			}
		}else{
			Utils.requirePlayer(sender, "replacenear");
		}
		return true;
	}
	
}