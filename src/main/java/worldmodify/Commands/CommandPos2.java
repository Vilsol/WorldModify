package worldmodify.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.PlayerSession;
import worldmodify.Utils;
import worldmodify.WMBukkit;

public class CommandPos2 implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.pos2")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
			ps.setPos2(((Player) sender).getLocation());
			sender.sendMessage(Utils.prefix + "Position 2 set!");
		}else{
			Utils.requirePlayer(sender, "pos2");
		}
		return true;
	}
	
}
