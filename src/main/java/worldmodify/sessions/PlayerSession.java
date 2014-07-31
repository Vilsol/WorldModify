package worldmodify.sessions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import worldmodify.WorldModify;
import worldmodify.menu.items.ItemSelectPosition;
import worldmodify.tools.Tool;

public class PlayerSession extends CommanderSession {

	private Player plr;
	private Class<? extends Tool> currentTool = ItemSelectPosition.class;
	
	public PlayerSession(Player player) {
		plr = player;
		WorldModify.playerSessions.put(plr, this);
	}

	/**
	 * Returns the session owner
	 * @return Session owner
	 */
	public Player getPlayer() {
		return plr;
	}
	
	public void setTool(Class<? extends Tool> t){
		if(t == null) return;
		for(ItemStack i : plr.getInventory().getContents()) {
			if(i != null && i.getType() == Material.WOOD_HOE){
				ItemMeta m = i.getItemMeta();
				m.setDisplayName(ChatColor.AQUA + "[" + ChatColor.GOLD + WorldModify.toolManager.getObject(t).getName() + ChatColor.AQUA + "]");
				i.setItemMeta(m);
			}
		}
		
		currentTool = t;
	}
	
	public Class<? extends Tool> getTool(){
		return currentTool;
	}
	
}
