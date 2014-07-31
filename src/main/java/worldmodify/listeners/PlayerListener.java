package worldmodify.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import worldmodify.WMBukkit;
import worldmodify.sessions.PlayerSession;
import worldmodify.tools.Tool;
import worldmodify.utils.Utils;

public class PlayerListener implements Listener {

	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent evt){
		if(!Utils.playerHasSession(evt.getPlayer())){
			WMBukkit.createPlayerSession(evt.getPlayer());
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent evt){
		final Player plr = evt.getPlayer();
		WMBukkit.createPlayerSession(plr);
		final PlayerSession ps = WMBukkit.getPlayerSession(plr);
		if(plr.getItemInHand().getType().equals(Material.WOOD_HOE)){
			Block target = evt.getPlayer().getTargetBlock(null, 256);
			if(!target.isEmpty()){
				Tool t = WMBukkit.getTool(plr);
				boolean result = false;
				if(evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					result = t.onToolRightClick(ps, target, evt.getBlockFace());
				}else if(evt.getAction().equals(Action.LEFT_CLICK_BLOCK) || evt.getAction().equals(Action.LEFT_CLICK_AIR)){
					result = t.onToolLeftClick(ps, target, evt.getBlockFace());
				}
				
				if(result) evt.setCancelled(true);
			}
		}
	}
	
}
