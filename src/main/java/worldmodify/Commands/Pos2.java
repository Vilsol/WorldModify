package worldmodify.Commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

public class Pos2 extends CommandBase {
	
	public Pos2() {
		super(".pos2", "", "wm.pos2");
	}
	
	public void execute(Player player, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(player);
		ps.setPos2((player).getLocation());
		player.sendMessage(Utils.prefix + "Position 2 set!");
	}

}
