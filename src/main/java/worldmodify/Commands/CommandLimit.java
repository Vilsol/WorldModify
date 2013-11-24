package worldmodify.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import worldmodify.WorldModify;
import worldmodify.utils.Utils;

public class CommandLimit implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(!sender.hasPermission("wm.limit")) return Utils.noPerms(sender);
		if(sender instanceof Player){
			if(args.length >= 1){
				if(Utils.isInteger(args[0])){
					WorldModify.limit = Integer.parseInt(args[0]);
					WorldModify.config.set("Config.GlobalLimit", WorldModify.limit);
					WorldModify.plugin.saveConfig();
				}else{
					sender.sendMessage(Utils.prefixe + "Limit must be a number!");
				}
			}
		}else{
			Utils.requirePlayer(sender, "limit");
		}
		return true;
	}
	
}