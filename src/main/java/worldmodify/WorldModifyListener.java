package worldmodify;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldModifyListener implements Listener {

	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent evt){
		if(!Utils.playerHasSession(evt.getPlayer())){
			WMBukkit.createPlayerSession(evt.getPlayer());
		}
	}
	
}
