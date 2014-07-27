package worldmodify.Commands;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import worldmodify.utils.Utils;

public class CommandManager implements Listener {
	
	public static CommandManager instance;
	protected JavaPlugin plugin;
	protected HashMap<String, ICommand> commands;
	
	public static void init(JavaPlugin plugin) {
		if(instance == null) {
			instance = new CommandManager(plugin);
		}
	}
	
	private CommandManager(JavaPlugin plugin) {
		this.plugin = plugin;
		this.commands = new HashMap<String, ICommand>();
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String commandName = event.getMessage().substring(1);
		String[] args = null;
		
		if(commandName.contains(" ")) {
			commandName = commandName.split(" ")[0];
			args = event.getMessage().substring(event.getMessage().indexOf(' ') + 1).split(" ");
		}
		
		ICommand command = commands.get(commandName.toLowerCase());
		
		if(command != null && event.getPlayer().hasPermission(command.permission())) {
			
			int argsLength = command.usage().split(" ").length;
			if(!command.usage().equals("")) {
				if(args == null || args.length < argsLength) {
					event.getPlayer().sendMessage(Utils.prefixe + "Usage: /" + command.command() + " " + command.usage());
					event.setCancelled(true);
					return;
				}
			}
			
			command.execute(event.getPlayer(), args);
			
			event.setCancelled(true);
		}
	}
	
	public void addCommand(ICommand command) {
		commands.put(command.command().toLowerCase(), command);
		command.setManager(this);
	}
	
	public void removeCommand(ICommand command) {
		commands.remove(command.command().toLowerCase());
		command.setManager(null);
	}

}
