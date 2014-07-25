package worldmodify.Commands;

import org.bukkit.entity.Player;

public abstract interface ICommand {
	
	/*
	 * The name of the command
	 */
	public abstract String command();
	
	/*
	 * Set's the command's manager
	 */
	public abstract void setManager(CommandManager paramManager);
	
	/*
	 * Execute the command
	 * @param paramPlayer the player that executed the command
	 * @param paramArrayOfString the arguments of the command
	 */
	public abstract void execute(Player paramPlayer, String[] paramArrayOfString);
	
	/*
	 * A string representing the usage of the command
	 */
	public abstract String usage();
	
	/*
	 * The permission node required to execute the command.
	 */
	public abstract String permission();

}
