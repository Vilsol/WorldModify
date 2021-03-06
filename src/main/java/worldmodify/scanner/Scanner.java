package worldmodify.scanner;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import worldmodify.R;
import worldmodify.WorldModify;
import worldmodify.sessions.CommanderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualArea;
import worldmodify.utils.VirtualBlock;

public class Scanner<T> extends BukkitRunnable {
	
	private ScannerRunner<T> returnClass;
	private boolean announceProgress;
	private CommanderSession cs;
	private Queue<VirtualBlock> blockList = new LinkedList<VirtualBlock>();
	private int yMod = 0;
	private int xMod = 0;
	private int zMod = 0;
	private int totalScanned = 0;
	private int announceWaiter = 0;
	private int bonusX = 0;
	private int bonusY = 0;
	private int bonusZ = 0;
	private int totalBlocks = 0;
	private Location high;
	private Location low;
	private boolean excludeAir = false;
	private T returnData;
	
	public Scanner(VirtualArea area, ScannerRunner<T> returnClass, boolean announceProgress, CommanderSession cs) {
		this.returnClass = returnClass;
		this.cs = cs;
		low = Utils.getLowPoint(area.getPos1(), area.getPos2());
		high = Utils.getHighestPoint(area.getPos1(), area.getPos2());
		totalBlocks = Utils.getTotalBlocks(low, high);
		xMod = low.getBlockX();
		yMod = low.getBlockY();
		zMod = low.getBlockZ();
		if(xMod == high.getBlockX()) bonusX = 1;
		if(yMod == high.getBlockY()) bonusY = 1;
		if(zMod == high.getBlockZ()) bonusZ = 1;
		if(high.getBlockX() - xMod >= 1) bonusX++;
		if(high.getBlockY() - yMod >= 1) bonusY++;
		if(high.getBlockZ() - zMod >= 1) bonusZ++;
	}
	
	public Scanner(VirtualArea area, ScannerRunner<T> returnClass) {
		new Scanner<T>(area, returnClass, false, null);
	}
	
	/**
	 * Sets whether or not to skip air.
	 * 
	 * @param excludeAir
	 */
	public void setExcludeAir(boolean excludeAir) {
		this.excludeAir = excludeAir;
	}
	
	public void setAnnounceProgress(boolean announceProgress){
		this.announceProgress = announceProgress;
	}
	
	public void setReturnData(T o){
		this.returnData = o;
	}
	
	public Object getReturnData(){
		return returnData;
	}
	
	/**
	 * Starts the scanner. Do not use .run()!
	 */
	public void scan() {
		this.runTaskTimer(WorldModify.plugin, 0L, 1L);
	}
	
	@Override
	public void run() {
		boolean stop = false;
		int localLimit = (Utils.getLocalLimit() * 5 >= 1000) ? Utils.getLocalLimit() * 5 : 1000;
		int current = 0;
		boolean firstRun = true;
		outer: while(current < localLimit && (current > 0 || firstRun)){
			firstRun = false;
			firstLoop: for(int Y = yMod; Y < high.getBlockY() + bonusY; Y++) {
				for(int X = xMod; X < high.getBlockX() + bonusX; X++) {
					for(int Z = zMod; Z < high.getBlockZ() + bonusZ; Z++) {
						if(current == localLimit) {
							yMod = Y;
							xMod = X;
							zMod = Z;
							break firstLoop;
						}
						
						if(totalScanned + current >= totalBlocks) break outer;
	
						current++;
						VirtualBlock vb = new VirtualBlock(low.getWorld().getBlockAt(X, Y, Z));
						if(excludeAir && vb.getMaterial() == Material.AIR) continue;
	
						stop = returnClass.scanBlock(vb.getBlock(), returnData);
						blockList.add(vb);
					}
					zMod = low.getBlockZ();
				}
				xMod = low.getBlockX();
			}
		}
		
		totalScanned += current;
		
		if(cs != null && announceProgress && announceWaiter == 20 && cs instanceof PlayerSession) {
			String message = R.prefix + "Scanning: [";
			double filled = Math.floor((((double) totalScanned / totalBlocks) * 100) / 5);
			for(int x = 0; x < 20; x++) {
				if(x < filled) {
					message += ChatColor.GREEN + "#";
				} else if(x == filled) {
					message += ChatColor.GREEN + ">";
				} else {
					message += ChatColor.RED + "-";
				}
			}
			message += ChatColor.AQUA + "] " + totalScanned + "/" + totalBlocks;
			
			((PlayerSession) cs).getPlayer().sendMessage(message);
			announceWaiter = 0;
		} else {
			announceWaiter++;
		}
		
		if(totalScanned >= totalBlocks) {
			returnClass.onFinish(blockList, cs, returnData);
			this.cancel();
		}
		
		if(stop) {
			this.cancel();
		}
	}
	
}
