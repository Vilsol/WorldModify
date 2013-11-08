package worldmodify.Commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.PlayerSession;
import worldmodify.StackFinder;
import worldmodify.Utils;
import worldmodify.WMBukkit;

public class CommandStack implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.stack")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			Player plr = (Player) sender;
			PlayerSession ps = WMBukkit.getPlayerSession(plr);
			if(ps.hasSetPos()){
				int times;
				if(args.length >= 1 && Utils.isInteger(args[0])){times = Integer.parseInt(args[0]);}else{times = 1;}
				int pi = Utils.dirToInt(plr.getLocation().getPitch());
				int ya = Utils.dirToInt(plr.getLocation().getYaw());
				
				final Map<String, Integer> size = Utils.getSelectionSize(ps.getPos1(), ps.getPos2());
				int alterx = 0;
				int altery = 0;
				int alterz = 0;

				if(pi == -1 || pi == 0 || pi == 1){
					if(ya == 5 || ya == 6 || ya == 7) alterx = size.get("xSize"); 
					if(ya == 1 || ya == 2 || ya == 3) alterx = size.get("xSize") * -1; 
					
					if(ya == 0 || ya == 1 || ya == 7 || ya == 8) alterz = size.get("zSize");
					if(ya == 3 || ya == 4 || ya == 5) alterz = size.get("zSize") * -1;
				}

				if(pi == -1 || pi == -2) altery = size.get("ySize");
				if(pi == 1 || pi == 2) altery = size.get("ySize") * -1;

				sender.sendMessage(Utils.prefix + "Scanning area...");
				
				boolean excludeAir = Utils.arrContains(args, "-a");
				
				new StackFinder(ps, alterx, altery, alterz, times, excludeAir);
			}
		}else{
			Utils.requirePlayer(sender, "stack");
		}
		return true;
	}
	
}