package worldmodify;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PlacerSession {

	private Location lowPoint;
	private Location highPoint;
	private List<VirtualBlock> blockList;
	private boolean running = false;
	private Integer task;
	private Integer totalBlocks;
	private Integer builtBlocks;
	private boolean buildTransparent = false;
	private boolean paused = false;
	
	public PlacerSession(List<VirtualBlock> vb, Location pos1, Location pos2) {
		lowPoint = Utils.getLowPoint(pos1, pos2);
		highPoint = Utils.getHighestPoint(pos1, pos2);
		totalBlocks = Utils.getTotalBlocks(lowPoint, highPoint);
	}

	/**
	 * Starts the build process
	 */
	public void build(){
		running = true;
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(WorldModify.plugin, new Runnable(){
			@Override
			public void run() {
				if(!paused){
					int blockCount = 0;
					boolean hasNoSolid = true;
					
					if(blockList.size() > 0){
						for(VirtualBlock vb : blockList){
							if(Utils.getLocalLimit() == blockCount){
								hasNoSolid = false;
								break;
							}
							
							if(!Utils.isTransparent(vb)){
								hasNoSolid = false;
								vb.buildBlock();
								blockCount++;
							}else{
								if(buildTransparent){
									vb.buildBlock();
									blockCount++;
								}
							}
							
							blockList.remove(vb);
						}
					}else{
						Bukkit.getScheduler().cancelTask(task);
						running = false;
					}
					
					builtBlocks += blockCount;
					
					if(hasNoSolid){
						buildTransparent = true;
					}
				}
			}
		}, 1L, 1L);
	}
	
	/**
	 * Checks if the builder is running
	 * @return is the 
	 */
	public boolean isDone(){
		return !running;
	}
	
	/**
	 * Returns the total blocks in the cuboid.
	 * @return Total blocks
	 */
	public Integer getTotalBlocks(){
		return totalBlocks;
	}
	
	/**
	 * Returns the completed blocks from this cuboid.
	 * @return Completed blocks
	 */
	public Integer getBuiltBlocks(){
		return builtBlocks;
	}
	
}
