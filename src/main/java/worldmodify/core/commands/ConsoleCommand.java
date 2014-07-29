package worldmodify.core.commands;

import org.bukkit.command.ConsoleCommandSender;

public interface ConsoleCommand {
	
	public boolean onCommand(ConsoleCommandSender s, String l, String[] args);
	
}
