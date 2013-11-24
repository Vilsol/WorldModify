package worldmodify.scanners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import worldmodify.WMBukkit;
import worldmodify.WorldModify;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.CommanderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class ReplaceScanner {

	public List<VirtualBlock> blockList = new ArrayList<VirtualBlock>();
	public boolean done = false;
	public int waiter = 0;
	public int yMod = 0;
	public int xMod = 0;
	public int zMod = 0;
	public int checked = 0;
	public int announceWaiter = 0;
	public int bonus = 0;
	
	public ReplaceScanner(Location pos1, Location pos2, final VirtualBlock replace, final VirtualBlock replacement, final CommanderSession cs){
		final Location low = Utils.getLowPoint(pos1, pos2);
		final Location high = Utils.getHighestPoint(pos1, pos2);
		final int totalBlocks = Utils.getTotalBlocks(low, high);
		xMod = low.getBlockX();
		yMod = low.getBlockY();
		zMod = low.getBlockZ();
		if(totalBlocks <= 1){
			bonus = 1;
		}
		waiter = Bukkit.getScheduler().scheduleSyncRepeatingTask(WorldModify.plugin, new Runnable(){
			@Override
			public void run() {
				int current = 0;
				firstLoop: for(int Y = yMod; Y < high.getBlockY() + bonus; Y++){
					for(int X = xMod; X < high.getBlockX() + bonus; X++){
						for(int Z = zMod; Z < high.getBlockZ() + bonus; Z++){
							if(current == Utils.getLocalLimit() * 5){
								yMod = Y;
								xMod = X;
								zMod = Z;
								break firstLoop;
							}
							VirtualBlock checkRep = new VirtualBlock(low.getWorld().getBlockAt(X, Y, Z));
							if(checkRep.getMaterial().equals(replace.getMaterial())){
								VirtualBlock newBlock = new VirtualBlock(replacement);
								newBlock.setLocation(new Location(low.getWorld(), X, Y, Z));
								blockList.add(newBlock);
							}
							current++;
						}
					}
				}
				
				checked += current;
				
				if(announceWaiter == 20 && cs instanceof PlayerSession){
					String message = Utils.prefix + "Searching: [";
					double filled = Math.floor((( (double) checked / totalBlocks) * 100) / 5);
					for(int x = 0; x < 20; x++){
						if(x < filled){
							message += ChatColor.GREEN + "#";
						}else if(x == filled){
							message += ChatColor.GREEN + ">";
						}else{
							message += ChatColor.RED + "-";
						}
					}
					message += ChatColor.AQUA + "] " + checked + "/" + totalBlocks;
					
					((PlayerSession) cs).getPlayer().sendMessage(message);
					announceWaiter = 0;
				}else{
					announceWaiter++;
				}
				
				if(checked >= totalBlocks){
					BuilderSession bs = WMBukkit.makeBuilderSession(blockList, cs);
					if(Utils.isTransparent(replace)) bs.reverseList();
					bs.build();
					if(cs instanceof PlayerSession){
						PlayerNotify pn = new PlayerNotify(((PlayerSession) cs).getPlayer(), bs);
						pn.infoMessage();
						pn.runMessenger();
					}
					Bukkit.getScheduler().cancelTask(waiter);
				}
			}
		}, 0L, 1L);
	}
	
}
