package worldmodify.Commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

public class Pos1 extends CommandBase {
	
	public Pos1() {
		super(".pos1", "", "wm.pos1");
	}
	
	public void execute(Player player, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(player);
		ps.setPos1(player.getLocation());
		player.sendMessage(Utils.prefix + "Position 1 set!");
	}

}
