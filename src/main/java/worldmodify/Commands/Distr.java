package worldmodify.Commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

public class Distr extends CommandBase {
	
	public Distr() {
		super(".distr", "", "wm.distr");
	}
	
	public void execute(Player player, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(player);
		if(ps.hasSetPos()){
			new DistrScanner(ps);
		}
	}

}
