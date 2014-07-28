package worldmodify.commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class CommandPaste implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.paste")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			Player plr = (Player) sender;
			PlayerSession ps = WMBukkit.getPlayerSession(plr);
			if(ps.hasSetPos()){
				if(ps.getClipboard() != null){
					if(ps.getRelativeCopy() != null){
						Location lowPoint = plr.getLocation().add(ps.getRelativeCopy());
						Queue<VirtualBlock> newList = Utils.alterBlockPosition(ps.getClipboard(), Utils.getRelativeCoords(lowPoint, ps.getCopyLocation()));
						BuilderSession bs = new BuilderSession(newList, ps);
						bs.build();
						PlayerNotify pn = new PlayerNotify((Player) sender, bs);
						pn.infoMessage();
						pn.runMessenger();
					}
				}
			}
		}else{
			Utils.requirePlayer(sender, "paste");
		}
		return true;
	}
}
