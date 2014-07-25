package worldmodify.Commands;

import org.bukkit.entity.Player;

import worldmodify.WMBukkit;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.utils.Utils;

public class Undo extends CommandBase {
	
	public Undo() {
		super(".undo", "", "wm.undo");
	}
	
	public void execute(Player player, String[] args) {
		BuilderSession bs = WMBukkit.getPlayerSession(player).undoHistory();
		if(bs != null){
			bs.saveUndo(false);
			bs.reverseList();
			bs.build();
			PlayerNotify pn = new PlayerNotify(player, bs);
			pn.infoMessage();
			pn.runMessenger();
		}else{
			player.sendMessage(Utils.prefixe + "Nothing to undo!");
		}
	}

}
