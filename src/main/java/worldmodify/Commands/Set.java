package worldmodify.Commands;

import java.util.Queue;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class Set extends CommandBase {

	public Set() {
		super(".set", "<block>", "wm.set");
	}

	@SuppressWarnings("deprecation")
	public void execute(Player player, String[] args) {
		PlayerSession ps = WMBukkit.getPlayerSession(player);
		if (ps.hasSetPos()) {
			
			if(Utils.getMaterial(args[0]) == null) {
				player.sendMessage(Utils.prefixe + "Invalid block!");
				return;
			}
			
			VirtualBlock vb = new VirtualBlock(Utils.getMaterial(args[0]));
			Queue<VirtualBlock> blockList = WMBukkit.getVirtualCuboid(ps, vb);
			BuilderSession bs = WMBukkit.makeBuilderSession(blockList, ps);
			/*if (Integer.parseInt(args[0]) == 0)
				bs.reverseList();*/
			bs.build();
			PlayerNotify pn = new PlayerNotify((Player) player, bs);
			pn.infoMessage();
			pn.runMessenger();
		} else {
			player.sendMessage(Utils.prefixe + "Please set both positions!");
		}
	}

}
