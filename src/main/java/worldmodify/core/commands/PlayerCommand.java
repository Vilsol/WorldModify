package worldmodify.core.commands;

import org.bukkit.entity.Player;

public interface PlayerCommand {

	public boolean onCommand(Player p, String l, String[] args);
	
}
