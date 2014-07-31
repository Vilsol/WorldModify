package worldmodify.commands;

import java.util.Queue;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import worldmodify.R;
import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.VirtualBlock;

@CMD(name = ".set", permission = "wm.set")
public class CommandSet extends CommandModel implements PlayerCommand {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		if(args.length >= 1){
			PlayerSession ps = WMBukkit.getPlayerSession(p);
			if(ps.hasSetPos()){
				VirtualBlock vb = new VirtualBlock(Material.getMaterial(Integer.parseInt(args[0])));
				Queue<VirtualBlock> blockList = WMBukkit.getVirtualCuboid(ps, vb);
				BuilderSession bs = WMBukkit.makeBuilderSession(blockList, ps);
				if(Integer.parseInt(args[0]) == 0) bs.reverseList();
				bs.build();
				PlayerNotify pn = new PlayerNotify(p, bs);
				pn.infoMessage();
				pn.runMessenger();
			}else{
				p.sendMessage(R.prefixe + "Please set both positions!");
			}
		}
		return true;
	}
	
}
