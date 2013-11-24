package worldmodify.notifiers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import worldmodify.WorldModify;
import worldmodify.sessions.BuilderSession;
import worldmodify.utils.Utils;

public class PlayerNotify {

	private BuilderSession bs;
	private Player plr;
	private int messager;
	
	public PlayerNotify(Player player, BuilderSession builderSession){
		bs = builderSession;
		plr = player;
	}
	
	/**
	 * Get the message with current information.
	 * @return Progress message
	 */
	public String getMessage(){
		String message = Utils.prefix + "Progress: [";
		double filled = Math.floor((( (double) bs.getBuiltBlocks() / bs.getTotalBlocks()) * 100) / 5);
		for(int x = 0; x < 20; x++){
			if(x < filled){
				message += ChatColor.GREEN + "#";
			}else if(x == filled){
				message += ChatColor.GREEN + ">";
			}else{
				message += ChatColor.RED + "-";
			}
		}
		message += ChatColor.AQUA + "] " + bs.getBuiltBlocks() + "/" + bs.getTotalBlocks();
		
		return message; 
	}
	
	/**
	 * Runs the messenger loop.
	 */
	public void runMessenger(){
		messager = Bukkit.getScheduler().scheduleSyncRepeatingTask(WorldModify.plugin, new Runnable(){
			@Override
			public void run() {
				if(!bs.isDone()){
					if(plr.getItemInHand().getType().equals(Material.WOOD_HOE)){
						plr.sendMessage(getMessage());
					}
				}else{
					Bukkit.getScheduler().cancelTask(messager);
				}
			}
		}, 0L, 20L);
	}

	public void infoMessage() {
		plr.sendMessage(Utils.prefix + "Started a builder with " + ChatColor.GREEN + bs.getTotalBlocks() + ChatColor.AQUA + " blocks!");
	}
	
}
