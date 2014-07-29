package worldmodify.commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.sessions.PlayerSession;

@CMD(permission = "wm.distr", name = ".distr")
public class CommandDistr extends CommandModel implements PlayerCommand {

	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(p);
		if(ps.hasSetPos()){
			new DistrScanner(ps);
		}
		return true;
	}
}
