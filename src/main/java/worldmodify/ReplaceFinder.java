package worldmodify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class ReplaceFinder {

	public List<VirtualBlock> blockList = new ArrayList<VirtualBlock>();
	public boolean done = false;
	public int waiter = 0;
	public int yMod = 0;
	public int xMod = 0;
	public int zMod = 0;
	public int checked = 0;
	public int announceWaiter = 0;
	public int bonus = 0;
	
	public ReplaceFinder(Location pos1, Location pos2, final VirtualBlock replace, final VirtualBlock replacement, final PlayerSession ps){
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
				
				if(announceWaiter == 20){
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
					
					ps.getPlayer().sendMessage(message);
					announceWaiter = 0;
				}else{
					announceWaiter++;
				}
				
				if(checked >= totalBlocks){
					BuilderSession bs = WMBukkit.makeBuilderSession(blockList, ps);
					if(Utils.isTransparent(replace)) bs.reverseList();
					bs.build();
					PlayerNotify pn = new PlayerNotify(ps.getPlayer(), bs);
					pn.infoMessage();
					pn.runMessenger();
					Bukkit.getScheduler().cancelTask(waiter);
				}
			}
		}, 0L, 1L);
	}
	
	public ReplaceFinder(Location pos1, Location pos2, final VirtualBlock replace, final VirtualBlock replacement){
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
				
				if(checked >= totalBlocks){
					BuilderSession bs = WMBukkit.makeBuilderSession(blockList);
					if(Utils.isTransparent(replace)) bs.reverseList();
					bs.build();
					Bukkit.getScheduler().cancelTask(waiter);
				}
			}
		}, 0L, 1L);
	}
	
}
