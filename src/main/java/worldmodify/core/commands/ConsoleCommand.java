package worldmodify.core.commands;

import org.bukkit.command.CommandSender;


public interface ConsoleCommand {
	
	public boolean onCommand(CommandSender s, String l, String[] args);
	
}
