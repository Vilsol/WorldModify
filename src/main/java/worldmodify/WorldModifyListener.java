package worldmodify;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldModifyListener implements Listener {

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
			Block target = evt.getPlayer().getTargetBlock(null, 100);
			if(!target.isEmpty()){
				if(evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					if(target.getLocation().equals(ps.getPos2())) return;
					ps.setPos2(target.getLocation());
					plr.sendMessage(Utils.prefix + "Position 2 set!");
					Utils.flashBlock(target.getLocation());
					evt.setCancelled(true);
				}else if(evt.getAction().equals(Action.LEFT_CLICK_BLOCK) || evt.getAction().equals(Action.LEFT_CLICK_AIR)){
					if(target.getLocation().equals(ps.getPos1())) return;
					ps.setPos1(target.getLocation());
					plr.sendMessage(Utils.prefix + "Position 1 set!");
					Utils.flashBlock(target.getLocation());
					evt.setCancelled(true);
				}
			}
		}
	}
	
}
