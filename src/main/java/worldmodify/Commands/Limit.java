package worldmodify.Commands;

import org.bukkit.entity.Player;

import worldmodify.WorldModify;
import worldmodify.utils.Utils;

public class Limit extends CommandBase {

	public Limit() {
		super(".limit", "<limit>", "wm.limit");
	}

	public void execute(Player player, String[] args) {
		if (Utils.isInteger(args[0])) {
			WorldModify.limit = Integer.parseInt(args[0]);
			WorldModify.config.set("Config.GlobalLimit", WorldModify.limit);
			WorldModify.plugin.saveConfig();
		} else {
			player.sendMessage(Utils.prefixe + "Limit must be a number!");
		}
	}

}
