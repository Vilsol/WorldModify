package worldmodify.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.PlayerSession;
import worldmodify.Utils;
import worldmodify.WMBukkit;

public class CommandPos1 implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.pos1")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
			ps.setPos1(((Player) sender).getLocation());
			sender.sendMessage(Utils.prefix + "Position 1 set!");
		}else{
			Utils.requirePlayer(sender, "pos1");
		}
		return true;
	}
	
}