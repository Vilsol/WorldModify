package worldmodify;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;


public class CopyScanner {

	public List<VirtualBlock> blockList = new ArrayList<VirtualBlock>();
	public boolean done = false;
	public int waiter = 0;
	public int yMod = 0;
	public int xMod = 0;
	public int zMod = 0;
	public int checked = 0;
	public int announceWaiter = 0;
	public int bonusX = 0;
	public int bonusY = 0;
	public int bonusZ = 0;
	
	public CopyScanner(final PlayerSession ps, final boolean excludeAir) {
		final Location low = Utils.getLowPoint(ps.getPos1(), ps.getPos2());
		final Location high = Utils.getHighestPoint(ps.getPos1(), ps.getPos2());
		final int totalBlocks = Utils.getTotalBlocks(low, high);
		xMod = low.getBlockX();
		yMod = low.getBlockY();
		zMod = low.getBlockZ();
		if(xMod == high.getBlockX()) bonusX = 1;
		if(yMod == high.getBlockY()) bonusY = 1;
		if(zMod == high.getBlockZ()) bonusZ = 1;
		if(high.getBlockX() - xMod >= 1) bonusX++;
		if(high.getBlockY() - yMod >= 1) bonusY++;
		if(high.getBlockZ() - zMod >= 1) bonusZ++;
		waiter = Bukkit.getScheduler().scheduleSyncRepeatingTask(WorldModify.plugin, new Runnable(){
			@Override
			public void run() {
				int localLimit = (Utils.getLocalLimit() * 5 >= 1000) ? Utils.getLocalLimit() * 5 : 1000;
				int current = 0;
				firstLoop: for(int Y = yMod; Y < high.getBlockY() + bonusY; Y++){
					for(int X = xMod; X < high.getBlockX() + bonusX; X++){
						for(int Z = zMod; Z < high.getBlockZ() + bonusZ; Z++){
							if(current == localLimit){
								yMod = Y;
								xMod = X;
								zMod = Z;
								break firstLoop;
							}

							VirtualBlock stacking = new VirtualBlock(low.getWorld().getBlockAt(X, Y, Z));
							if(excludeAir && stacking.getMaterial() == Material.AIR) continue;
							blockList.add(stacking);
							current++;
						}
					}
				}
				
				if(announceWaiter == 20){
					String message = Utils.prefix + "Scanning: [";
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
				
				checked += current;
				
				if(checked >= totalBlocks){
					ps.getPlayer().sendMessage(Utils.prefix + "Blocks copied!");
					ps.setClipboard(blockList);
					Bukkit.getScheduler().cancelTask(waiter);
				}
			}
		}, 0L, 1L);
	}
	
}
