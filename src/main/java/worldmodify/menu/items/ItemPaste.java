package worldmodify.menu.items;

import java.util.Arrays;
import java.util.Queue;

import me.vilsol.menuengine.engine.MenuItem;
import me.vilsol.menuengine.utils.Builder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import worldmodify.R;
import worldmodify.WMBukkit;
import worldmodify.WorldModify;
import worldmodify.notifiers.PlayerNotify;
import worldmodify.sessions.BuilderSession;
import worldmodify.sessions.PlayerSession;
import worldmodify.tools.Tool;
import worldmodify.utils.Utils;
import worldmodify.utils.VirtualBlock;

public class ItemPaste implements MenuItem, Tool {
	
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
		return new Builder(Material.REDSTONE_COMPARATOR).setName(R.title + "Paste").setLore(Arrays.asList(R.leftright + "Paste")).getItem();
	}

	@Override
	public boolean onToolLeftClick(PlayerSession p, Block b, BlockFace f) {
		b = b.getRelative(BlockFace.UP);
		if(p.hasSetPos()) {
			if(p.getClipboard() != null) {
				if(p.getRelativeCopy() != null) {
					Location lowPoint = b.getLocation().add(p.getRelativeCopy());
					Queue<VirtualBlock> newList = Utils.alterBlockPosition(p.getClipboard(), Utils.getRelativeCoords(lowPoint, p.getCopyLocation()));
					BuilderSession bs = new BuilderSession(newList, p);
					bs.build();
					PlayerNotify pn = new PlayerNotify(p.getPlayer(), bs);
					pn.infoMessage();
					pn.runMessenger();
				}
			}
		}
		return true;
	}

	@Override
	public boolean onToolRightClick(PlayerSession p, Block b, BlockFace f) {
		return onToolLeftClick(p, b, f);
	}

	@Override
	public String getName() {
		return "Paste";
	}
	
}
