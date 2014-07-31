package worldmodify.commands;

import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

@CMD(name = ".paste", permission = "wm.paste")
public class CommandPaste extends CommandModel implements PlayerCommand {
	
	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(p);
		if(ps.hasSetPos()) {
			if(ps.getClipboard() != null) {
				if(ps.getRelativeCopy() != null) {
					boolean fast = Utils.arrContains(args, "-f");
					Location lowPoint = p.getLocation().add(ps.getRelativeCopy());
					Queue<VirtualBlock> newList = Utils.alterBlockPosition(ps.getClipboard(), Utils.getRelativeCoords(lowPoint, ps.getCopyLocation()));
					BuilderSession bs = new BuilderSession(newList, ps);
					bs.build();
					PlayerNotify pn = new PlayerNotify(p, bs);
					pn.infoMessage();
					pn.runMessenger();
				}
			}
		}
		return true;
	}
}
