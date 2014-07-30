package worldmodify.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import worldmodify.WorldModify;
import worldmodify.utils.Utils;

public abstract class CommandModel implements CommandExecutor {
	
	private CMD a;
	
	public CommandModel() {
		if(!(this instanceof ConsoleCommand) && !(this instanceof PlayerCommand)) throw new InvalidCommandConfigException(this);
		
		this.a = this.getClass().getAnnotation(CMD.class);
		if(this.a == null) throw new CommandAnnotationNotFoundException(this);
		
		PluginCommand cmd = WorldModify.plugin.getCommand(a.name());
		if(cmd == null) throw new CommandNotFoundException(this);
		
		cmd.setExecutor(this);
		WorldModify.d("Loaded Command: " + a.name());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(a.permission() != null && !a.permission().equals("")){
			if(!sender.hasPermission(a.permission())){
				Utils.noPerms(sender);
				return true;
			}
		}
		
		boolean result = false;
		
		if(this instanceof PlayerCommand){
			if(!(sender instanceof Player)){
				Utils.requirePlayer(sender, label);
				return true;
			}else{
				result = ((PlayerCommand) this).onCommand((Player) sender, label, args);
			}
		}else{
			result = ((ConsoleCommand) this).onCommand(sender, label, args);
		}
		
		if(!result){
			// TODO Show Usage
		}
		
		return true;
	}
	
	@SuppressWarnings("serial")
	public class InvalidCommandConfigException extends RuntimeException {
		
		public InvalidCommandConfigException(CommandModel m) {
			super(m.getClass().getName() + " Has to implement " + ConsoleCommand.class.getName() + " or " + PlayerCommand.class.getName());
		}
		
	}
	
	@SuppressWarnings("serial")
	public class CommandNotFoundException extends RuntimeException {
		
		public CommandNotFoundException(CommandModel m) {
			super(m.getClass().getName() + " tried to use un-configured command: " + a.name());
		}
		
	}
	
	@SuppressWarnings("serial")
	public class CommandAnnotationNotFoundException extends RuntimeException {
		
		public CommandAnnotationNotFoundException(CommandModel m) {
			super(m.getClass().getName() + " does not contain a command annotation");
		}
		
	}
	
}
