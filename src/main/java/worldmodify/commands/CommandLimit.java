package worldmodify.commands;

import org.bukkit.command.CommandSender;

import worldmodify.R;
import worldmodify.WorldModify;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.ConsoleCommand;
import worldmodify.utils.Utils;

@CMD(name = ".limit", permission = "wm.limit")
public class CommandLimit extends CommandModel implements ConsoleCommand {
	
	@Override
	public boolean onCommand(CommandSender s, String l, String[] args) {
		if(args.length >= 1) {
			if(Utils.isInteger(args[0])) {
				WorldModify.limit = Integer.parseInt(args[0]);
				WorldModify.config.set("Config.GlobalLimit", WorldModify.limit);
				WorldModify.plugin.saveConfig();
			} else {
				s.sendMessage(R.prefixe + "Limit must be a number!");
			}
		}
		return true;
	}
	
}