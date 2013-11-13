package worldmodify.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.CopyScanner;
import worldmodify.PlayerSession;
import worldmodify.Utils;
import worldmodify.WMBukkit;

public class CommandCopy implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.copy")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			Player plr = (Player) sender;
			PlayerSession ps = WMBukkit.getPlayerSession(plr);
			if(ps.hasSetPos()){
				boolean excludeAir = Utils.arrContains(args, "-a");
				Location low = Utils.getLowPoint(ps.getPos1(), ps.getPos2());
				ps.setRelativeCopy(Utils.getRelativeCoords(low, plr.getLocation()));
				ps.setCopyLocation(Utils.getLowPoint(ps.getPos1(), ps.getPos2()));
				new CopyScanner(ps, excludeAir);
			}
		}else{
			Utils.requirePlayer(sender, "stack");
		}
		return true;
	}

}
