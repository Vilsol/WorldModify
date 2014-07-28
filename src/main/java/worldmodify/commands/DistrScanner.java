package worldmodify.commands;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import worldmodify.WorldModify;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;

public class DistrScanner {
	
	public Map<Material, Integer> blockList = new HashMap<Material, Integer>();
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
	
	public DistrScanner(final PlayerSession ps) {
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

							Block cv = low.getWorld().getBlockAt(X, Y, Z);
							if(blockList.containsKey(cv.getType())){
								blockList.put(cv.getType(), blockList.get(cv.getType()) + 1);
							}else{
								blockList.put(cv.getType(), 1);
							}
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
					ps.getPlayer().sendMessage(Utils.prefix + "Block distribution:");
					int count = 1;
					blockList = Utils.sortByValue(blockList);
					for(Material m : blockList.keySet()){
						ps.getPlayer().sendMessage(Utils.prefix + count + ". (" + Double.valueOf(new DecimalFormat("#.##").format((blockList.get(m).doubleValue()/totalBlocks)*100.0)) + "%) " + m.toString() + " - " + blockList.get(m));
						count++;
					}
					Bukkit.getScheduler().cancelTask(waiter);
				}
			}
		}, 0L, 1L);
	}

}
