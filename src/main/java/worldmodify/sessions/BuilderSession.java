package worldmodify.sessions;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.scheduler.BukkitRunnable;

import worldmodify.R;
import worldmodify.WorldModify;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class BuilderSession extends BukkitRunnable {
	
	private Queue<VirtualBlock> blockList;
	private boolean done = false;
	private Integer totalBlocks;
	private Integer builtBlocks = 0;
	private boolean paused = false;
	private CommanderSession commanderSession;
	private Queue<VirtualBlock> replaced = new LinkedList<VirtualBlock>();
	private Queue<VirtualBlock> transparent = new LinkedList<VirtualBlock>();
	private Queue<VirtualBlock> delayed = new LinkedList<VirtualBlock>();
	private boolean saveUndo = true;
	private boolean buildTransparent = false;
	private boolean buildDelayed = false;
	
	public BuilderSession(Queue<VirtualBlock> vb, CommanderSession cs) {
		totalBlocks = vb.size();
		blockList = vb;
		commanderSession = cs;
	}
	
	@Override
	public void run() {
		if(paused) {
			WorldModify.builderSessions.remove(this);
			this.cancel();
			return;
		}
		
		Queue<VirtualBlock> q = null;
		if(buildTransparent) {
			q = transparent;
		} else if(buildDelayed) {
			q = delayed;
		} else {
			q = blockList;
		}
		
		int blockCount = 0;
		if(q != null && q.size() > 0) {
			while(q.size() > 0) {
				if(Utils.getLocalLimit() == blockCount) break;
				VirtualBlock vb = q.peek();
				
				if(buildDelayed) {
					vb.delayActions();
					q.remove();
					continue;
				}
				
				if(!Utils.isSameVirtualBlock(vb, new VirtualBlock(vb.getLocation().getBlock()))) {
					if(!Utils.isTransparent(vb)) {
						boolean delayed = vb.buildBlock();
						if(delayed) {
							this.delayed.add(vb);
						}
						replaced.add(new VirtualBlock(vb.getLocation().getBlock()));
						blockCount++;
					} else {
						if(buildTransparent) {
							boolean delayed = vb.buildBlock();
							if(delayed) {
								this.delayed.add(vb);
							}
							replaced.add(new VirtualBlock(vb.getLocation().getBlock()));
							blockCount++;
						} else {
							transparent.add(vb);
						}
					}
				} else {
					blockCount++;
				}
				
				q.remove();
			}
			builtBlocks += blockCount;
		} else {
			if(builtBlocks >= totalBlocks) {
				if(commanderSession != null) {
					if(commanderSession instanceof PlayerSession) {
						((PlayerSession) commanderSession).getPlayer().sendMessage(R.prefix + "Done!");
					}
					if(saveUndo) commanderSession.addToHistory(replaced);
				}
				WorldModify.builderSessions.remove(this);
				this.cancel();
				done = true;
			} else {
				if(buildTransparent) {
					if(delayed.size() > 0) {
						
					} else {
						if(blockList.size() > 0) {
							buildTransparent = false;
						}
					}
				} else {
					buildTransparent = true;
				}
			}
		}
		
	}
	
	/**
	 * Starts the build process
	 */
	public void build() {
		done = false;
		WorldModify.builderSessions.add(this);
		this.runTaskTimer(WorldModify.plugin, 0L, 1L);
	}
	
	/**
	 * Reverses the block list
	 */
	public void reverseList() {
		blockList = Utils.reverseQueue(blockList);
	}
	
	/**
	 * Checks if the builder is running
	 * 
	 * @return Is done
	 */
	public boolean isDone() {
		return done;
	}
	
	/**
	 * Returns the total blocks in the cuboid.
	 * 
	 * @return Total blocks
	 */
	public Integer getTotalBlocks() {
		return totalBlocks;
	}
	
	/**
	 * Returns the completed blocks from this cuboid.
	 * 
	 * @return Completed blocks
	 */
	public Integer getBuiltBlocks() {
		return builtBlocks;
	}
	
	/**
	 * Returns the commander who initiated the builder
	 * 
	 * @return Player session or null
	 */
	public CommanderSession getCommanderSession() {
		return commanderSession;
	}
	
	/**
	 * Returns if the builder is paused
	 * 
	 * @return Is paused
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Pauses the builder
	 */
	public void pause() {
		paused = true;
	}
	
	/**
	 * Unpauses the builder
	 */
	public void unpause() {
		paused = false;
		build();
	}
	
	/**
	 * Set whether or not to save an undo
	 * 
	 * @param save
	 *            Save or not
	 */
	public void saveUndo(boolean save) {
		saveUndo = save;
	}
	
}
