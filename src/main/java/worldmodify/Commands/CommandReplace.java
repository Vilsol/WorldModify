package worldmodify.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.PlayerSession;
import worldmodify.ReplaceFinder;
import worldmodify.Utils;
import worldmodify.VirtualBlock;
import worldmodify.WMBukkit;

public class CommandReplace implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.replace")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			if(args.length >= 2){
				PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
				if(ps.hasSetPos()){
					VirtualBlock replace = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[0])));
					VirtualBlock replacement = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[1])));
					sender.sendMessage(Utils.prefix + "Detecting replacements...");
					new ReplaceFinder(ps.getPos1(), ps.getPos2(), replace, replacement, ps); 
				}else{
					sender.sendMessage(Utils.prefixe + "Please set both positions!");
				}
			}
		}else{
			Utils.requirePlayer(sender, "replace");
		}
		return true;
	}
	
}