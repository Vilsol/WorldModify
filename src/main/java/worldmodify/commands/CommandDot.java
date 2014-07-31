package worldmodify.commands;

import me.vilsol.menuengine.engine.MenuModel;

import org.bukkit.entity.Player;

import worldmodify.core.commands.CMD;
import worldmodify.core.commands.CommandModel;
import worldmodify.core.commands.PlayerCommand;
import worldmodify.menu.MiddleMenu;

@CMD(name = ".", permission = "wm.menu")
public class CommandDot extends CommandModel implements PlayerCommand {

	@Override
	public boolean onCommand(Player p, String l, String[] args) {
		MenuModel.menus.get(MiddleMenu.class).getMenu().showToPlayer(p);
		return true;
	}
	
}
