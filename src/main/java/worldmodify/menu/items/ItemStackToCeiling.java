package worldmodify.menu.items;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import me.vilsol.menuengine.engine.MenuItem;
import me.vilsol.menuengine.utils.Builder;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import worldmodify.R;
import worldmodify.WMBukkit;
import worldmodify.WorldModify;
import worldmodify.sessions.PlayerSession;
import worldmodify.tools.Tool;
import worldmodify.utils.VirtualBlock;

public class ItemStackToCeiling implements MenuItem, Tool {

	@Override
	public void registerItem() {
		MenuItem.items.put(this.getClass(), this);
		WorldModify.toolManager.registerObject(this.getClass(), this);
	}

	@Override
	public void execute(Player plr, ClickType click) {
		WMBukkit.getPlayerSession(plr).setTool(this.getClass());
		plr.closeInventory();
	}

	@Override
	public ItemStack getItem() {
		return new Builder(Material.LADDER).setName(R.title + "Stack To Ceiling").setLore(Arrays.asList(R.leftright + "Stack To Ceiling")).getItem();
	}

	@Override
	public boolean onToolLeftClick(PlayerSession p, Block b, BlockFace f) {
		VirtualBlock blueprint = new VirtualBlock(b);
		b = b.getRelative(BlockFace.UP);
		Queue<VirtualBlock> toPlace = new LinkedList<VirtualBlock>();
		while(b.getType() == Material.AIR){
			VirtualBlock v = new VirtualBlock(blueprint);
			v.setLocation(b.getLocation());
			toPlace.add(v);
			b = b.getRelative(BlockFace.UP);
		}
		WMBukkit.makeBuilderSession(toPlace, p).build();
		return true;
	}

	@Override
	public boolean onToolRightClick(PlayerSession p, Block b, BlockFace f) {
		return onToolLeftClick(p, b, f);
	}

	@Override
	public String getName() {
		return "Stack To Ceiling";
	}
	
}
