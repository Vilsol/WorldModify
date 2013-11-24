package worldmodify.Commands;

import java.util.List;

import org.bukkit.Material;
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

public class CommandSet implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.set")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			if(args.length >= 1){
				PlayerSession ps = WMBukkit.getPlayerSession((Player) sender);
				if(ps.hasSetPos()){
					VirtualBlock vb = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[0])));
					List<VirtualBlock> blockList = WMBukkit.getVirtualCuboid(ps, vb);
					BuilderSession bs = WMBukkit.makeBuilderSession(blockList, ps);
					if(Integer.parseInt(args[0]) == 0) bs.reverseList();
					bs.build();
					PlayerNotify pn = new PlayerNotify((Player) sender, bs);
					pn.infoMessage();
					pn.runMessenger();
				}else{
					sender.sendMessage(Utils.prefixe + "Please set both positions!");
				}
			}
		}else{
			Utils.requirePlayer(sender, "set");
		}
		return true;
	}
	
}
