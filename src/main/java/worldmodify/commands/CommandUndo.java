package worldmodify.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.utils.Utils;

public class CommandUndo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.undo")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			BuilderSession bs = WMBukkit.getPlayerSession((Player) sender).undoHistory();
			if(bs != null){
				bs.saveUndo(false);
				bs.reverseList();
				bs.build();
				PlayerNotify pn = new PlayerNotify((Player) sender, bs);
				pn.infoMessage();
				pn.runMessenger();
			}else{
				sender.sendMessage(Utils.prefixe + "Nothing to undo!");
			}
		}else{
			Utils.requirePlayer(sender, "undo");
		}
		return true;
	}
	
}