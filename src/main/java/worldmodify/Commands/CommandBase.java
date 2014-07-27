package worldmodify.Commands;


public abstract class CommandBase implements ICommand {
	
	String command, usage, permission;
	CommandManager manager;
	
	public CommandBase(String command, String usage, String permission) {
		this.command = command;
		this.usage = usage;
		this.permission = permission;
	}
	
	public String command() {
		return command;
	}
	
	public String usage() {
		return usage;
	}
	
	public String permission() {
		return permission;
	}
	
	public void setManager(CommandManager manager) {
		this.manager = manager;
	}

}
