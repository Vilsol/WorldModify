package worldmodify.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import worldmodify.utils.Utils;

public class Help extends CommandBase {
	
	public Help() {
		super(".help", "", "wm.help");
	}
	
	public void execute(Player player, String[] args) {
		player.sendMessage(Utils.prefix + "Commands");
		for(String commandName : this.manager.commands.keySet()) {
			String str = ChatColor.AQUA + "/" + commandName + " ";
			str += ChatColor.GRAY + this.manager.commands.get(commandName.toLowerCase()).usage();
			player.sendMessage(str);
		}
	}

}
