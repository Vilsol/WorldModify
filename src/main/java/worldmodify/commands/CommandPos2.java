package worldmodify.commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

@CMD(name = ".pos2", permission = "wm.pos2")
public class CommandPos2 extends CommandModel implements PlayerCommand {
	
	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(p);
		ps.setPos2((p).getLocation());
		p.sendMessage(Utils.prefix + "Position 2 set!");
		return true;
	}
	
}
