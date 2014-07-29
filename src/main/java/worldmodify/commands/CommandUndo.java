package worldmodify.commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.utils.Utils;

@CMD(name = ".undo", permission = "wm.undo")
public class CommandUndo extends CommandModel implements PlayerCommand {

	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		BuilderSession bs = WMBukkit.getPlayerSession(p).undoHistory();
		if(bs != null){
			bs.saveUndo(false);
			bs.reverseList();
			bs.build();
			PlayerNotify pn = new PlayerNotify(p, bs);
			pn.infoMessage();
			pn.runMessenger();
		}else{
			p.sendMessage(Utils.prefixe + "Nothing to undo!");
		}
		return true;
	}
	
}