package worldmodify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;

public class BuilderSession {

	private List<VirtualBlock> blockList;
	private boolean done = false;
	private Integer task;
	private Integer totalBlocks;
	private Integer builtBlocks = 0;
	private boolean buildTransparent = false;
	private boolean paused = false;
	private PlayerSession playerSession;
	private List<VirtualBlock> replaced = new ArrayList<VirtualBlock>();
	private boolean saveUndo = true;
	
	public BuilderSession(List<VirtualBlock> vb) {
		totalBlocks = vb.size();
		blockList = vb;
	}

	public BuilderSession(List<VirtualBlock> vb, PlayerSession ps) {
		totalBlocks = vb.size();
		blockList = vb;
		playerSession = ps;
	}
	
	/**
	 * Starts the build process
	 */
	public void build(){
		done = false;
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(WorldModify.plugin, new Runnable(){
			@Override
			public void run() {
				if(!paused){
					int blockCount = 0;
					boolean hasNoSolid = true;
					Iterator<VirtualBlock> i = blockList.iterator();
					
					if(blockList != null && blockList.size() > 0){
						while(i.hasNext()){
							VirtualBlock vb = i.next();

							if(Utils.getLocalLimit() == blockCount){
								hasNoSolid = false;
								break;
							}
							
							replaced.add(new VirtualBlock(vb.getLocation().getBlock()));
							
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

							i.remove();
						}
						builtBlocks += blockCount;
					}else{
						if(playerSession != null){
							playerSession.getPlayer().sendMessage(Utils.prefix + "Done!");
							if(saveUndo) playerSession.addToHistory(replaced);
						}
						Bukkit.getScheduler().cancelTask(task);
						done = true;
					}
					
					if(hasNoSolid){
						buildTransparent = true;
					}
					
				}else{
					Bukkit.getScheduler().cancelTask(task);
				}
			}
		}, 1L, 1L);
	}
	
	/**
	 * Checks if the builder is running
	 * @return Is done 
	 */
	public boolean isDone(){
		return done;
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
	
	/**
	 * Returns the player who initiated the builder
	 * @return Player session or null
	 */
	public PlayerSession getPlayerSession(){
		return playerSession;
	}
	
	/**
	 * Returns if the builder is paused
	 * @return Is paused
	 */
	public boolean isPaused(){
		return paused;
	}
	
	/**
	 * Pauses the builder
	 */
	public void pause(){
		paused = true;
	}
	
	/**
	 * Unpauses the builder
	 */
	public void unpause(){
		paused = false;
		build();
	}
	
	/**
	 * Set whether or not to save an undo
	 * @param save Save or not
	 */
	public void saveUndo(boolean save){
		saveUndo = save;
	}
	
}
