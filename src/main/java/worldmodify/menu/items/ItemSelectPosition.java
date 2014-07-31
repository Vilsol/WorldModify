package worldmodify.menu.items;

import java.util.Arrays;

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
import worldmodify.utils.Utils;

public class ItemSelectPosition implements MenuItem, Tool {

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
		return new Builder(Material.REDSTONE_TORCH_ON).setName(R.title + "Set Positions").setLore(Arrays.asList(R.left + "Select Position 1", R.right + "Select Position 2")).getItem();
	}

	@Override
	public boolean onToolLeftClick(PlayerSession p, Block b, BlockFace f) {
		if(b.getLocation().equals(p.getPos1())) return false;
		p.setPos1(b.getLocation());
		p.getPlayer().sendMessage(R.prefix + "Position 1 set!");
		Utils.flashBlock(b.getLocation());
		return true;
	}

	@Override
	public boolean onToolRightClick(PlayerSession p, Block b, BlockFace f) {
		if(b.getLocation().equals(p.getPos2())) return false;
		p.setPos2(b.getLocation());
		p.getPlayer().sendMessage(R.prefix + "Position 2 set!");
		Utils.flashBlock(b.getLocation());
		return true;
	}

	@Override
	public String getName() {
		return "Select Position";
	}
	
}
