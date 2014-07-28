package worldmodify.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

public class CommandDistr  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.distr")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
			if(ps.hasSetPos()){
				new DistrScanner(ps);
			}
		}else{
			Utils.requirePlayer(sender, "ditr");
		}
		return true;
	}
}
