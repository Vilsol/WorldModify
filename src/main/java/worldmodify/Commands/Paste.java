package worldmodify.Commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class Paste extends CommandBase {

	public Paste() {
		super(".paste", "", "wm.paste");
	}

	public void execute(Player player, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(player);
		if (ps.hasSetPos()) {
			if (ps.getClipboard() != null) {
				if (ps.getRelativeCopy() != null) {
					Location lowPoint = player.getLocation().add(ps.getRelativeCopy());
					Queue<VirtualBlock> newList = Utils.alterBlockPosition(ps.getClipboard(), Utils.getRelativeCoords(lowPoint, ps.getCopyLocation()));
					BuilderSession bs = new BuilderSession(newList, ps);
					bs.build();
					PlayerNotify pn = new PlayerNotify(player, bs);
					pn.infoMessage();
					pn.runMessenger();
				}
			}
		}
	}

}
