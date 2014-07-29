package worldmodify.commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

@CMD(name = ".pos1", permission = "wm.pos1")
public class CommandPos1 extends CommandModel implements PlayerCommand {
	
	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(p);
		ps.setPos1((p).getLocation());
		p.sendMessage(Utils.prefix + "Position 1 set!");
		return true;
	}
	
}