package worldmodify.commands;

import java.util.Queue;

import org.bukkit.entity.Player;

import worldmodify.R;
import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

@CMD(name = ".undo", permission = "wm.undo")
public class CommandUndo extends CommandModel implements PlayerCommand {

	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		Queue<VirtualBlock> undoBlocks = WMBukkit.getPlayerSession(p).popHistory();
		if(undoBlocks != null && undoBlocks.size() > 0){
			if(args.length > 0 && Utils.isInteger(args[0])){
				PlayerSession ps = WMBukkit.getPlayerSession(p);
				for(int i = 0; i < Integer.parseInt(args[0]) - 1; i++) {
					Queue<VirtualBlock> q = ps.popHistory();
					if(q == null) break;
					undoBlocks.addAll(q);
				}
			}
			
			BuilderSession bs = WMBukkit.makeBuilderSession(undoBlocks, WMBukkit.getPlayerSession(p));
			bs.saveUndo(false);
			bs.reverseList();
			bs.build();
			PlayerNotify pn = new PlayerNotify(p, bs);
			pn.infoMessage();
			pn.runMessenger();
		}else{
			p.sendMessage(R.prefixe + "Nothing to undo!");
		}
		return true;
	}
	
}